package com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateClientDTO {
    private String name;
    private String email;
}
