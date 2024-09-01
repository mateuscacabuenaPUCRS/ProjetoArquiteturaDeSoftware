package com.projarq.trabalho01_clean.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppEntity {

    //Código identificador do aplicativo
    private Long id; 
    //Nome do aplicativo
    private String name;
    //Valor da assinatura mensal
    private float monthlyCost;
}
