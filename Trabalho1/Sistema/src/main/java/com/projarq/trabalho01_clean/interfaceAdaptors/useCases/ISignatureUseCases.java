package com.projarq.trabalho01_clean.interfaceAdaptors.useCases;

import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Signature.SignatureType;
import com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Signature.SignatureResponse;

public interface ISignatureUseCases {
    SignatureResponse addSignature (Long clientId, Long appId) throws IllegalArgumentException;
    List<SignatureResponse> getAllSignatures();
    SignatureResponse getSignature(Long signatureId) throws EmptyResultDataAccessException;
    List<SignatureResponse> getClientSignatures(Long clientID) throws IllegalArgumentException;
    List<SignatureResponse> getSignatureByType(SignatureType type);
    List<SignatureResponse> getAppSignatures(Long appId) throws IllegalArgumentException;
    boolean isSignatureActive(Long signatureId) throws EmptyResultDataAccessException;
    void updateSignature(Long signatureId, Date endDate) throws EmptyResultDataAccessException;
    void cancelSignature(Long signatureId) throws EmptyResultDataAccessException;
}