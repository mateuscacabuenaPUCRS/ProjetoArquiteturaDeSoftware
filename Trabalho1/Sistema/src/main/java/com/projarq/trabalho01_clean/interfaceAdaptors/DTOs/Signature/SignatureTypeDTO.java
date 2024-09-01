package com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Signature;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SignatureTypeDTO {
    private String type;

    public boolean isValid() {
        String[] validTypes = {SignatureType.TODAS.getValue(), SignatureType.ATIVAS.getValue(), SignatureType.CANCELADAS.getValue()};
        for (String validType : validTypes) {
            if (type.toLowerCase().equals(validType.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public SignatureType getSignatureType() {
        if (!isValid()) {
            return null;
        }
        return SignatureType.valueOf(type.toUpperCase());
    }
}
