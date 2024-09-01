package com.projarq.trabalho01_clean.interfaceAdaptors.repository;

import java.util.Date;
import java.util.List;

import com.projarq.trabalho01_clean.domain.entity.PaymentEntity;

public interface IPaymentRepository{
    PaymentEntity create(Long signatureId, float signaturePrice, Date paymentDate, String promotion);
    List<PaymentEntity> getAll();
}
