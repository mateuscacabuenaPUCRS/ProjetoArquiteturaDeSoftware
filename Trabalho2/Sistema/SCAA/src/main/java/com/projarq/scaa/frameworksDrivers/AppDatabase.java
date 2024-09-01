package com.projarq.scaa.frameworksDrivers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.scaa.domain.entity.AppEntity;
import com.projarq.scaa.domain.entity.ClientEntity;
import com.projarq.scaa.interfaceAdaptors.DTOs.App.EditAppDTO;
import com.projarq.scaa.interfaceAdaptors.repository.IAppRepository;

@Repository
public class AppDatabase implements IAppRepository {
    private JdbcTemplate database;

    @Autowired
    public AppDatabase(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public AppEntity create(String name, float monthlyCost) {
        String sql = "INSERT INTO apps (name, monthlyCost) VALUES (?, ?)";
        int appDBId = database.update(sql, name, monthlyCost);
        Long appId = Long.valueOf(appDBId);
        return new AppEntity(appId, name, monthlyCost);
    }

    @Override
    public AppEntity edit(Long id, EditAppDTO app) {
        String sql = "UPDATE apps SET";
        if (app.getName() != null) {
            sql += " name = '" + app.getName() + "',";
        }
        if (app.getMonthlyCost() != null) {
            sql += " monthlyCost = " + app.getMonthlyCost() + ",";
        }
        // Remove a última vírgula
        sql = sql.substring(0, sql.length() - 1);
        sql += " WHERE id = " + id;
        int appDBId = database.update(sql);
        Long appId = Long.valueOf(appDBId);
        return getApp(appId);
    }

    @Override
    public List<AppEntity> getAll() {
        String sql = "SELECT * FROM apps";
        return database.query(sql, (rs, rowNum) -> new AppEntity(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getFloat("monthlyCost")
        ));
    }

    @Override
    public AppEntity getApp(Long appId) {
        String sql = "SELECT * FROM apps WHERE id = ?";
        return database.queryForObject(sql, (rs, rowNum) -> new AppEntity(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getFloat("monthlyCost")
        ), appId);
    }

    @Override
    public List<ClientEntity> getAllClients(Long appId) {
        String sql = "SELECT * FROM clients WHERE appId = ?";
        return database.query(sql, (rs, rowNum) -> new ClientEntity(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email")
        ));
    }

    @Override
    public AppEntity updateMonthlyCost(Long appId, float monthlyCost) {
        String sql = "UPDATE apps SET monthlyCost = ? WHERE id = ?";
        database.update(sql, monthlyCost, appId);
        return getApp(appId);
    }
}
