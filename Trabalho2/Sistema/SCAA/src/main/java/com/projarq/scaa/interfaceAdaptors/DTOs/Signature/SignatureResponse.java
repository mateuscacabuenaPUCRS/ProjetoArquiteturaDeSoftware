package com.projarq.scaa.interfaceAdaptors.DTOs.Signature;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignatureResponse {
    private Long id;
    private Long appId;
    private Long clientId;
    private Date startDate;
    private Date endDate;
}
