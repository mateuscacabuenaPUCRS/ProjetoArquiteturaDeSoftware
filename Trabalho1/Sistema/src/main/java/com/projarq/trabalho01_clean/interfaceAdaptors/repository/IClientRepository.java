package com.projarq.trabalho01_clean.interfaceAdaptors.repository;

import java.util.List;

import com.projarq.trabalho01_clean.domain.entity.ClientEntity;

public interface IClientRepository{
    ClientEntity create(String name, String email);
    List<ClientEntity> getAllClients();
    ClientEntity getClient(Long id);
    ClientEntity edit(Long id, String name, String email);
}
