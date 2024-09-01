package com.projarq.trabalho01_clean.interfaceAdaptors.useCases;

import java.util.Date;
import java.util.List;

import com.projarq.trabalho01_clean.domain.entity.PaymentEntity;
import com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Payment.PaymentResponseDTO;

public interface IPaymentUseCases {
    PaymentResponseDTO create(Long signatureId, float payedValue, Date paymentDate, String promotion) throws IllegalArgumentException;
    List<PaymentEntity> getAll();
}
