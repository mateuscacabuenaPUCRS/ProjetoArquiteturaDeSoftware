package com.projarq.scaa.interfaceAdaptors.repository;

import java.util.List;

import com.projarq.scaa.domain.entity.AppEntity;
import com.projarq.scaa.domain.entity.ClientEntity;
import com.projarq.scaa.interfaceAdaptors.DTOs.App.EditAppDTO;

public interface IAppRepository{
    AppEntity create(String name, float monthlyCost);
    List<AppEntity> getAll();
    AppEntity getApp(Long appId);
    List<ClientEntity> getAllClients(Long appId);
    AppEntity edit(Long id, EditAppDTO updatedAppEntity);
    AppEntity updateMonthlyCost(Long appId, float monthlyCost);
}
