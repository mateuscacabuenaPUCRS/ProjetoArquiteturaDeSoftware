package com.projarq.asscache.service;

import com.projarq.asscache.model.Signature;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {

    private final Map<String, Signature> cache = new ConcurrentHashMap<>();

    public Signature getSignature(String appId) {
        return cache.get(appId);
    }

    public void updateCache(Signature signature) {
        cache.put(signature.getId().toString(), signature);
    }
}
