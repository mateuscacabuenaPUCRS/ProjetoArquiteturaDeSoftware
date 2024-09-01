package com.projarq.asscache.controller;

import com.projarq.asscache.model.Signature;
import com.projarq.asscache.service.CacheService;
import com.projarq.asscache.service.SCAAProxy;
import com.projarq.asscache.service.SignatureService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class SignatureController {
    private SCAAProxy scaaProxy;
    private SignatureService signatureService;
    private CacheService cacheService;

    @Autowired
    public SignatureController(
        SCAAProxy scaaProxy,
        SignatureService signatureService,
        CacheService cacheService
    ) {
        this.scaaProxy = scaaProxy;
        this.signatureService = signatureService;
        this.cacheService = cacheService;
    }

    @GetMapping
    public String welcome() {
        return "AssCache: Signature Controller";
    }

    @GetMapping("/assinvalida/{codapp}")
    public boolean isSignatureActive(@PathVariable("codapp") Long appId) {
        return signatureService.isSignatureActive(appId);
    }

    @GetMapping("/{signatureId}")
    public ResponseEntity<Signature> checkSignature(@PathVariable String signatureId) {
        System.out.println("Checking signature for signatureId: " + signatureId);
        Signature signature = cacheService.getSignature(signatureId);
        if (signature == null) {
            try {
                Date expiryDate = scaaProxy.getSignatureExpiryDate(signatureId);
                signature = new Signature(
                    Long.parseLong(signatureId),
                    expiryDate.after(new Date()),
                    expiryDate
                );
            } catch (HttpClientErrorException e) {
                System.out.println("Error: " + e.getMessage());
                return ResponseEntity.notFound().build();
            }
            cacheService.updateCache(signature);
        }
        return ResponseEntity.ok(signature);
    }
}
