package com.projarq.scaa.interfaceAdaptors.DTOs.App;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAppDTO {
    private String name;
    private float monthlyCost;
}
