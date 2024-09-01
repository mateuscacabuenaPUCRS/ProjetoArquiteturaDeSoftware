package com.projarq.asscache.service;

import java.util.Date;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "scaa")
public interface SCAAProxy {
    @GetMapping("/assinaturas/validade/{signatureId}")
    public Date getSignatureExpiryDate(@PathVariable String signatureId);
}
