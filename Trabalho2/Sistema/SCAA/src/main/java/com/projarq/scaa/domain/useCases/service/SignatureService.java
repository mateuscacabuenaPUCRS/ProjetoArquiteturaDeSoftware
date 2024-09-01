package com.projarq.scaa.domain.useCases.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projarq.scaa.domain.entity.AppEntity;
import com.projarq.scaa.domain.entity.ClientEntity;
import com.projarq.scaa.domain.entity.SignatureEntity;
import com.projarq.scaa.interfaceAdaptors.DTOs.Signature.SignatureResponse;
import com.projarq.scaa.interfaceAdaptors.DTOs.Signature.SignatureType;
import com.projarq.scaa.interfaceAdaptors.repository.ISignatureRepository;
import com.projarq.scaa.interfaceAdaptors.useCases.IAppUseCases;
import com.projarq.scaa.interfaceAdaptors.useCases.IClientUseCases;
import com.projarq.scaa.interfaceAdaptors.useCases.ISignatureUseCases;

@Service
public class SignatureService implements ISignatureUseCases {
    private IAppUseCases appService;
    private IClientUseCases clientService;
    private ISignatureRepository signatureRepository;
    private RabbitTemplate rabbitTemplate;
    private FanoutExchange fanout;

    @Autowired
    public SignatureService(
        IAppUseCases appService,
        IClientUseCases clientService,
        ISignatureRepository signatureRepository,
        RabbitTemplate rabbitTemplate,
        FanoutExchange fanout
    ) {
        this.appService = appService;
        this.clientService = clientService;
        this.signatureRepository = signatureRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.fanout = fanout;
    }

    @Override
    public SignatureResponse addSignature(Long clientId, Long appId) throws IllegalArgumentException {
        ClientEntity client;
        try {
            client = clientService.getClient(clientId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Client not found");
        }
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        AppEntity app;
        try {
            app = appService.getApp(appId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("App not found");
        }
        if (app == null) {
            throw new IllegalArgumentException("App not found");
        }

        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        // Default = 30 days + 7 free days
        calendar.add(Calendar.DATE, 30 + 7);
        Date endDate = calendar.getTime();
        SignatureEntity signature = signatureRepository.addSignature(clientId, appId, startDate, endDate);
        return new SignatureResponse(
            signature.getId(),
            signature.getAppId(),
            signature.getClientId(),
            startDate,
            endDate
        );
    }

    @Override
    public List<SignatureResponse> getAllSignatures() {
        List<SignatureEntity> signatures = signatureRepository.getAllSignatures();
        return signatures.stream().map(signature -> {
            return new SignatureResponse(
                signature.getId(),
                signature.getAppId(),
                signature.getClientId(),
                signature.getStartDate(),
                signature.getEndDate()
            );
        }).toList();
    }

    @Override
    public SignatureResponse getSignature(Long signatureId) throws IllegalArgumentException {
        SignatureEntity signature;
        try {
            signature = signatureRepository.getSignature(signatureId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Signature not found");
        }
        return new SignatureResponse(
            signature.getId(),
            signature.getAppId(),
            signature.getClientId(),
            signature.getStartDate(),
            signature.getEndDate()
        );
    }

    @Override
    public List<SignatureResponse> getAppSignatures(Long appId) {
        List<SignatureEntity> signatures = signatureRepository.getAppSignatures(appId);
        return signatures.stream().map(signature -> {
            return new SignatureResponse(
                signature.getId(),
                signature.getAppId(),
                signature.getClientId(),
                signature.getStartDate(),
                signature.getEndDate()
            );
        }).toList();
    }

    @Override
    public List<SignatureResponse> getClientSignatures(Long clientID) {
        List<SignatureEntity> signatures;
        try {
            signatures = signatureRepository.getClientSignatures(clientID);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Client not found");
        }
        return signatures.stream().map(signature -> {
            return new SignatureResponse(
                signature.getId(),
                signature.getAppId(),
                signature.getClientId(),
                signature.getStartDate(),
                signature.getEndDate()
            );
        }).toList();
    }

    @Override
    public List<SignatureResponse> getSignatureByType(SignatureType type) {
        List<SignatureEntity> signatures = null;
        String comparator = null;
        Date comparingDate = null;
        if (type == SignatureType.ATIVAS) {
            comparator = ">=";
            comparingDate = new Date();
            signatures = signatureRepository.getSignatureByEndDate(comparator, comparingDate);
        } else if (type == SignatureType.CANCELADAS) {
            comparator = "<";
            comparingDate = new Date();
            signatures = signatureRepository.getSignatureByEndDate(comparator, comparingDate);
        } else {
            signatures = signatureRepository.getAllSignatures();
        }
        return signatures.stream().map(signature -> {
            return new SignatureResponse(
                signature.getId(),
                signature.getAppId(),
                signature.getClientId(),
                signature.getStartDate(),
                signature.getEndDate()
            );
        }).toList();
    }

    @Override
    public boolean isSignatureActive(Long signatureId) throws IllegalArgumentException {
        SignatureEntity signature;
        try {
            signature = signatureRepository.getSignature(signatureId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Signature not found");
        }
        Date now = new Date();
        Date endDate = signature.getEndDate();
        return endDate.after(now) || endDate.equals(now);
    }

    @Override
    public void updateSignature(Long signatureId, Date endDate) {
        try {
            // TODO: Check that endDate > startDate
            signatureRepository.updateSignature(signatureId, endDate);
            SignatureEntity signature = signatureRepository.getSignature(signatureId);
            rabbitTemplate.convertAndSend(fanout.getName(), "", signature);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Signature not found");
        }
    }

    @Override
    public void cancelSignature(Long signatureId) throws IllegalArgumentException {
        try {
            signatureRepository.updateSignature(signatureId, new Date());
            SignatureEntity signature = signatureRepository.getSignature(signatureId);
            rabbitTemplate.convertAndSend(fanout.getName(), "", signature);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Signature not found");
        }
    }
}
