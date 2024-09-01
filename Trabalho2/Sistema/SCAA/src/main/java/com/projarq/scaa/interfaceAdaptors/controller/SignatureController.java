package com.projarq.scaa.interfaceAdaptors.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projarq.scaa.interfaceAdaptors.DTOs.Signature.SignatureRequestDTO;
import com.projarq.scaa.interfaceAdaptors.DTOs.Signature.SignatureResponse;
import com.projarq.scaa.interfaceAdaptors.DTOs.Signature.SignatureType;
import com.projarq.scaa.interfaceAdaptors.DTOs.Signature.SignatureTypeDTO;
import com.projarq.scaa.interfaceAdaptors.useCases.ISignatureUseCases;

@CrossOrigin(origins = "*")
@RestController
public class SignatureController {
    private ISignatureUseCases signatureService;

    @Autowired
    public SignatureController(ISignatureUseCases signatureService) {
        this.signatureService = signatureService;
    }

    /** Cria uma assinatura */
    @PostMapping("/servcad/assinaturas")
    public ResponseEntity<SignatureResponse> addSignature(@RequestBody final SignatureRequestDTO signatureDTO) {
        Long clientId = signatureDTO.getClientId();
        Long appId = signatureDTO.getAppId();
        try {
            SignatureResponse signatureResponse = signatureService.addSignature(clientId, appId);
            return new ResponseEntity<>(
                signatureResponse,
                HttpStatus.CREATED
            );
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/servcad/assinaturas")
    public List<SignatureResponse> getAllSignatures() {
        List<SignatureResponse> signatures = signatureService.getAllSignatures();
        return signatures;
    }

    /** Retorna a lista com todas as assinaturas confirme o tipo */
    @GetMapping("/servcad/assinaturas/{tipo}")
    public ResponseEntity<List<SignatureResponse>> getSignatureByType(
        @PathVariable(value="tipo") String type
    ) {
        SignatureTypeDTO signatureTypeValue = new SignatureTypeDTO(type);
        if (!signatureTypeValue.isValid()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SignatureType signatureType = signatureTypeValue.getSignatureType();
        List<SignatureResponse> signatures = signatureService.getSignatureByType(signatureType);
        return new ResponseEntity<>(signatures, HttpStatus.OK);
    }

    /** Retorna a lista das assinaturas do cliente informado */
    @GetMapping("/servcad/asscli/{codcli}")
    public ResponseEntity<List<SignatureResponse>> getClientSignatures(
        @PathVariable(value="codcli") final Long clientId
    ) {
        List<SignatureResponse> clientSignatures;
        try {
            clientSignatures = signatureService.getClientSignatures(clientId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(clientSignatures, HttpStatus.OK);
    }

    /** Retorna a lista de assinaturas de um aplicativo */
    @GetMapping("/servcad/assapp/{codapp}")
    public List<SignatureResponse> getAppSignatures(
        @PathVariable(value="codapp") final Long appId
    ) {
        return signatureService.getAppSignatures(appId);
    }

    /** Retorna a data de validade de uma assinatura */
    @GetMapping("/assinaturas/validade/{codass}")
    public ResponseEntity<Date> getSignatureEndDate(
        @PathVariable(value="codass") final Long signatureId
    ) {
        try {
            SignatureResponse signature = signatureService.getSignature(signatureId);
            return new ResponseEntity<Date>(signature.getEndDate(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Retorna se a assinatura questionada permanece ativa */
    @GetMapping("/assinvalida/{codass}")
    public ResponseEntity<Boolean> isSignatureActive(
        @PathVariable(value="codass") final Long signatureId
    ) {
        try {
            boolean isActive = signatureService.isSignatureActive(signatureId);
            return new ResponseEntity<>(isActive, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/servcad/assinaturas/{codass}")
    public ResponseEntity<Void> cancelSignature(
        @PathVariable(value="codass") final Long signatureId
    ) {
        try {
            signatureService.cancelSignature(signatureId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
