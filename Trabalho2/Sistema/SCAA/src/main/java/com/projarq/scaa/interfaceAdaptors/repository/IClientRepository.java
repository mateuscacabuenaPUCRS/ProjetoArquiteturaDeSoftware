package com.projarq.scaa.interfaceAdaptors.repository;

import java.util.List;

import com.projarq.scaa.domain.entity.ClientEntity;

public interface IClientRepository{
    ClientEntity create(String name, String email);
    List<ClientEntity> getAllClients();
    ClientEntity getClient(Long id);
    ClientEntity edit(Long id, String name, String email);
}
