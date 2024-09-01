package com.projarq.trabalho01_clean.interfaceAdaptors.useCases;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.projarq.trabalho01_clean.domain.entity.ClientEntity;

public interface IClientUseCases {
    ClientEntity create(String name, String email);
    List<ClientEntity> getAllClients();
    ClientEntity getClient(Long id) throws EmptyResultDataAccessException;
    ClientEntity edit(Long id, String name, String email) throws IllegalArgumentException;
}
