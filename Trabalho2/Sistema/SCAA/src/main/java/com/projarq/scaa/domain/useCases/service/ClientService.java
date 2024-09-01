package com.projarq.scaa.domain.useCases.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projarq.scaa.domain.entity.ClientEntity;
import com.projarq.scaa.interfaceAdaptors.repository.IClientRepository;
import com.projarq.scaa.interfaceAdaptors.useCases.IClientUseCases;

@Service
public class ClientService implements IClientUseCases {
    private IClientRepository clientRepository;

    @Autowired
    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientEntity create(String name, String email) {
        return clientRepository.create(name, email);
    }

    @Override
    public List<ClientEntity> getAllClients() {
        return clientRepository.getAllClients();
    }

    @Override
    public ClientEntity getClient(Long id) throws EmptyResultDataAccessException {
        return clientRepository.getClient(id);
    }

    @Override
    public ClientEntity edit(Long id, String name, String email) throws IllegalArgumentException {
        return clientRepository.edit(id, name, email);
    }
}
