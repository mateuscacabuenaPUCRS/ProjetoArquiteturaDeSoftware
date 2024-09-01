package com.projarq.trabalho01_clean.interfaceAdaptors.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projarq.trabalho01_clean.domain.entity.PaymentEntity;
import com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Payment.PaymentRequestDTO;
import com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.Payment.PaymentResponseDTO;
import com.projarq.trabalho01_clean.interfaceAdaptors.useCases.IPaymentUseCases;

@CrossOrigin(origins = "*")
@RestController
public class PaymentController {
    private IPaymentUseCases paymentService;

    @Autowired
    public PaymentController(IPaymentUseCases paymentService) {
        this.paymentService = paymentService;
    }

    /** Solicita o registro de um pagamento */
    @PostMapping("/registrarpagamento")
    public ResponseEntity<PaymentResponseDTO> addPayment(@RequestBody final PaymentRequestDTO paymentDTO) {
        try {
            PaymentResponseDTO paymentResponse = paymentService.create(
                paymentDTO.getSignatureId(),
                paymentDTO.getPayedValue(),
                new Date(),
                paymentDTO.getPromotion()
            );
            return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Lista todos os pagamentos */
    @GetMapping("/pagamentos")
    public List<PaymentEntity> getPayments() {
        List<PaymentEntity> payments = paymentService.getAll();
        return payments;
    }
}
