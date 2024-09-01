package com.projarq.scaa.interfaceAdaptors.DTOs.Payment;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long signatureId;
    private float payedValue;
    private String promotion;
}
