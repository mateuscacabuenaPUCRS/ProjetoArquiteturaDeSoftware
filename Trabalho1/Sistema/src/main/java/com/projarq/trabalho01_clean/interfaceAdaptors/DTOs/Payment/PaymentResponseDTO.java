package com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Payment;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private Date paymentDate;
    private float refoundValue;
    private PaymentStatus status;
}
