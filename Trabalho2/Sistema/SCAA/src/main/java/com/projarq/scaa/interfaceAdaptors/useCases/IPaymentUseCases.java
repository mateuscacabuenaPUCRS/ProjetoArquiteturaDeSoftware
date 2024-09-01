package com.projarq.scaa.interfaceAdaptors.useCases;

import java.util.Date;
import java.util.List;

import com.projarq.scaa.domain.entity.PaymentEntity;
import com.projarq.scaa.interfaceAdaptors.DTOs.Payment.PaymentResponseDTO;

public interface IPaymentUseCases {
    PaymentResponseDTO create(Long signatureId, float payedValue, Date paymentDate, String promotion) throws IllegalArgumentException;
    List<PaymentEntity> getAll();
}
