package com.projarq.scaa.domain.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignatureEntity {

    //Código da assinatura
    private Long id;
    //Aplicativo de que trata a assinatura
    private Long appId;
    //Cliente de que trata a assinatura
    private Long clientId;
    //Início da vigência da assinatura
    private Date startDate;
    //Fim da vigência da assinatura
    private Date endDate;
}