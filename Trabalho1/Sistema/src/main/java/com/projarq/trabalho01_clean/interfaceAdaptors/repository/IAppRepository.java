package com.projarq.trabalho01_clean.interfaceAdaptors.repository;

import java.util.List;

import com.projarq.trabalho01_clean.domain.entity.AppEntity;
import com.projarq.trabalho01_clean.domain.entity.ClientEntity;
import com.projarq.trabalho01_clean.interfaceAdaptors.DTOs.App.EditAppDTO;

public interface IAppRepository{
    AppEntity create(String name, float monthlyCost);
    List<AppEntity> getAll();
    AppEntity getApp(Long appId);
    List<ClientEntity> getAllClients(Long appId);
    AppEntity edit(Long id, EditAppDTO updatedAppEntity);
    AppEntity updateMonthlyCost(Long appId, float monthlyCost);
}
