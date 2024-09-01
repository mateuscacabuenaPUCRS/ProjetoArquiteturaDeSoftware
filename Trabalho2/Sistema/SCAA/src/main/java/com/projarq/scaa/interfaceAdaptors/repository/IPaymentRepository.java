package com.projarq.scaa.interfaceAdaptors.repository;

import java.util.Date;
import java.util.List;

import com.projarq.scaa.domain.entity.PaymentEntity;

public interface IPaymentRepository{
    PaymentEntity create(Long signatureId, float signaturePrice, Date paymentDate, String promotion);
    List<PaymentEntity> getAll();
}
