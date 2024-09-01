package com.projarq.scaa.domain.useCases.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projarq.scaa.domain.entity.AppEntity;
import com.projarq.scaa.domain.entity.PaymentEntity;
import com.projarq.scaa.interfaceAdaptors.DTOs.Payment.PaymentResponseDTO;
import com.projarq.scaa.interfaceAdaptors.DTOs.Payment.PaymentStatus;
import com.projarq.scaa.interfaceAdaptors.DTOs.Signature.SignatureResponse;
import com.projarq.scaa.interfaceAdaptors.repository.IPaymentRepository;
import com.projarq.scaa.interfaceAdaptors.useCases.IAppUseCases;
import com.projarq.scaa.interfaceAdaptors.useCases.IPaymentUseCases;
import com.projarq.scaa.interfaceAdaptors.useCases.ISignatureUseCases;

@Service
public class PaymentService implements IPaymentUseCases {
    private IPaymentRepository paymentRepository;
    private IAppUseCases appService;
    private ISignatureUseCases signatureService;

    @Autowired
    public PaymentService(
        IPaymentRepository paymentRepository,
        IAppUseCases appService,
        ISignatureUseCases signatureService
    ) {
        this.paymentRepository = paymentRepository;
        this.appService = appService;
        this.signatureService = signatureService;
    }

    @Override
    public PaymentResponseDTO create(Long signatureId, float payedValue, Date paymentDate, String promotion) throws IllegalArgumentException {
        SignatureResponse signature;
        try {
            signature = signatureService.getSignature(signatureId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Assinatura não encontrada");
        }
        if (signature == null) {
            throw new IllegalArgumentException("Assinatura não encontrada");
        }

        Long appId = signature.getAppId();
        AppEntity app;
        try {
            app = appService.getApp(appId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("App não encontrado");
        }
        if (app == null) {
            throw new IllegalArgumentException("App não encontrado");
        }

        if (payedValue < app.getMonthlyCost()) {
            float refoundValue = payedValue;
            return new PaymentResponseDTO(paymentDate, refoundValue, PaymentStatus.VALOR_INCORRETO);
        }

        if (payedValue > app.getMonthlyCost()) {
            float refoundValue = payedValue - app.getMonthlyCost();
            return new PaymentResponseDTO(paymentDate, refoundValue, PaymentStatus.VALOR_INCORRETO);
        }

        if (promotion == "") {
            return new PaymentResponseDTO(paymentDate, payedValue, PaymentStatus.PROMOCAO_INVALIDA);
        }

        Calendar calendar = Calendar.getInstance();
        if (paymentDate.before(signature.getEndDate())) {
            // Se o pagamento for dado dentro do período
            calendar.setTime(signature.getEndDate());
        } else {
            // Se a assinatura já tiver sido cancelada
            calendar.setTime(paymentDate);
        }
        // startTime + 30 Days
        calendar.add(Calendar.DATE, 30);
        Date endDate = calendar.getTime();
        signatureService.updateSignature(signatureId, endDate);

        paymentRepository.create(signatureId, payedValue, paymentDate, promotion);
        return new PaymentResponseDTO(endDate, 0, PaymentStatus.PAGAMENTO_OK);
    }

    @Override
    public List<PaymentEntity> getAll() {
        return paymentRepository.getAll();
    }
}
