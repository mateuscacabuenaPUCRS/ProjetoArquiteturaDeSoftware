package com.projarq.trabalho01_clean.frameworksDrivers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.trabalho01_clean.domain.entity.ClientEntity;
import com.projarq.trabalho01_clean.interfaceAdaptors.repository.IClientRepository;

@Repository
public class ClientDatabase implements IClientRepository {
    private JdbcTemplate database;

    @Autowired
    public ClientDatabase(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public ClientEntity create(String name, String email) {
        String sql = "INSERT INTO clients (name, email) VALUES (?, ?)";
        int clientDBId = database.update(sql, name, email);
        Long clientId = Long.valueOf(clientDBId);
        return new ClientEntity(clientId, name, email);
    }

    @Override
    public ClientEntity edit(Long id, String name, String email) {
        String sql = "UPDATE clients SET";
        if (name != null) {
            sql += " name = '" + name + "',";
        }
        if (email != null) {
            sql += " email = '" + email + "',";
        }
        // Remove a última vírgula
        sql = sql.substring(0, sql.length() - 1);
        sql += " WHERE id = " + id;
        int clientDBId = database.update(sql);
        Long clientId = Long.valueOf(clientDBId);
        return getClient(clientId);
    }

    @Override
    public List<ClientEntity> getAllClients() {
        String sql = "SELECT * FROM clients";
        return database.query(sql, (rs, rowNum) -> new ClientEntity(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email")
        ));
    }

    @Override
    public ClientEntity getClient(Long clientId) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        return database.queryForObject(sql, (rs, rowNum) -> new ClientEntity(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email")
        ), clientId);
    }
}
