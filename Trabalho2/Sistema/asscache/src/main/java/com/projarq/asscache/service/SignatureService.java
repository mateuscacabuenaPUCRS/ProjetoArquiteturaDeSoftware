package com.projarq.asscache.service;

import com.projarq.asscache.model.Signature;
import com.projarq.asscache.repository.SignatureRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SignatureService {
    @Autowired
    private SignatureRepository signatureRepository;

    public boolean isSignatureActive(Long signatureId) {
        Optional<Signature> signatureOpt = signatureRepository.findById(signatureId);
        if (signatureOpt.isPresent()) {
            Signature signature = signatureOpt.get();
            if (signature.getExpiryDate().after(new Date())) {
                return signature.isActive();
            }
        }
        // Se não encontrar no cache, consulta o SCAA
        // Código para consultar o SCAA e armazenar no cache
        return false;
    }

    @RabbitListener(queues = "#{rabbitMQConfig.queue().getName()}")
    public void handleSignatureUpdate(String message) {
        Long signatureId = Long.parseLong(message);
        signatureRepository.deleteById(signatureId);
    }
}
