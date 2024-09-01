package com.projarq.asscache.service;

import com.projarq.asscache.model.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheListener {
    private static final Logger logger = LoggerFactory.getLogger(CacheListener.class);

    private final CacheService cacheService;

    @Autowired
    public CacheListener(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @RabbitListener(queues = "#{rabbitMQConfig.queue().getName()}")
    public void receive(Signature signature) {
        logger.info("Mensagem recebida com a atualização da assinatura: {}", signature);
        cacheService.updateCache(signature);
    }
}
