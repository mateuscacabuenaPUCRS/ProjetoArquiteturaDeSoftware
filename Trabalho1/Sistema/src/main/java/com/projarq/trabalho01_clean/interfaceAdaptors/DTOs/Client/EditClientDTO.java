package com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Client;

import lombok.Data;

@Data
public class EditClientDTO {
    private String name;
    private String email;

    public EditClientDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}