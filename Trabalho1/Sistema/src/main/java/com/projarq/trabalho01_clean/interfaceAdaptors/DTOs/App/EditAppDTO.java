package com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.App;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditAppDTO {
    private String name;
    private Float monthlyCost;

    public EditAppDTO(String name) {
        this.name = name;
        this.monthlyCost = null;
    }

    public EditAppDTO(Float monthlyCost) {
        this.name = null;
        this.monthlyCost = monthlyCost;
    }
}
