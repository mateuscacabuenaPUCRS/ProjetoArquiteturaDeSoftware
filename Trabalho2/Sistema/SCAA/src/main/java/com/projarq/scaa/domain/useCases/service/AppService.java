package com.projarq.scaa.domain.useCases.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projarq.scaa.domain.entity.AppEntity;
import com.projarq.scaa.domain.entity.ClientEntity;
import com.projarq.scaa.interfaceAdaptors.DTOs.App.EditAppDTO;
import com.projarq.scaa.interfaceAdaptors.repository.IAppRepository;
import com.projarq.scaa.interfaceAdaptors.useCases.IAppUseCases;

@Service
public class AppService implements IAppUseCases {
    private IAppRepository appRepository;

    @Autowired
    public AppService(IAppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public AppEntity create(String name, float monthlyCost) {
        return appRepository.create(name, monthlyCost);
    }

    @Override
    public List<AppEntity> getAll() {
        return appRepository.getAll();
    }

    @Override
    public AppEntity getApp(Long appId) throws EmptyResultDataAccessException {
        return appRepository.getApp(appId);
    }

    @Override
    public List<ClientEntity> getAllClients(Long appId) throws IllegalArgumentException {
        return appRepository.getAllClients(appId);
    }

    @Override
    public AppEntity edit(Long id, EditAppDTO updatedAppEntity) throws IllegalArgumentException {
        return appRepository.edit(id, updatedAppEntity);
    }

    @Override
    public AppEntity updateMonthlyCost(Long appId, float monthlyCost) throws IllegalArgumentException {
        return appRepository.updateMonthlyCost(appId, monthlyCost);
    }
}
