package com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Signature;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignatureRequestDTO {
    private Long clientId;
    private Long appId;
}
