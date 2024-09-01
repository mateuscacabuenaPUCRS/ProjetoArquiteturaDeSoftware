package com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Signature;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SignatureType {
    TODAS("todas"),
    ATIVAS("ativas"),
    CANCELADAS("canceladas");

    private String value;

    public String getValue() {
        return value;
    }
}
