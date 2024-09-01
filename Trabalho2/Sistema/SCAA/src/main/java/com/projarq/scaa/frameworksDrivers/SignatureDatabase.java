package com.projarq.scaa.frameworksDrivers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.scaa.domain.entity.SignatureEntity;
import com.projarq.scaa.interfaceAdaptors.repository.ISignatureRepository;

@Repository
public class SignatureDatabase implements ISignatureRepository {
    private JdbcTemplate database;

    @Autowired
    public SignatureDatabase(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public SignatureEntity addSignature(Long clientId, Long appId, Date startDate, Date endDate) {
        String sql = "INSERT INTO signatures (clientId, appId, startDate, endDate) VALUES (?, ?, ?, ?)";
        int signatureDBId = database.update(sql, clientId, appId, startDate, endDate);
        Long signatureId = Long.valueOf(signatureDBId);
        return getSignature(signatureId);
    }

    @Override
    public List<SignatureEntity> getAllSignatures() {
        String sql = "SELECT * FROM signatures";
        return database.query(sql, (rs, rowNum) -> new SignatureEntity(
            rs.getLong("id"),
            rs.getLong("appId"),
            rs.getLong("clientId"),
            rs.getDate("startDate"),
            rs.getDate("endDate")
        ));
    }

    @Override
    public SignatureEntity getSignature(Long signatureId) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM signatures WHERE id = ?";
        return database.queryForObject(sql, (rs, rowNum) -> new SignatureEntity(
            rs.getLong("id"),
            rs.getLong("appId"),
            rs.getLong("clientId"),
            rs.getDate("startDate"),
            rs.getDate("endDate")
        ), signatureId);
    }

    @Override
    public List<SignatureEntity> getAppSignatures(Long appId) {
        String sql = "SELECT * FROM signatures WHERE appId = ?";
        return database.query(sql, (rs, rowNum) -> new SignatureEntity(
            rs.getLong("id"),
            rs.getLong("appId"),
            rs.getLong("clientId"),
            rs.getDate("startDate"),
            rs.getDate("endDate")
        ), appId);
    }

    @Override
    public List<SignatureEntity> getClientSignatures(Long clientID) {
        String sql = "SELECT * FROM signatures WHERE clientId = ?";
        return database.query(sql, (rs, rowNum) -> new SignatureEntity(
            rs.getLong("id"),
            rs.getLong("appId"),
            rs.getLong("clientId"),
            rs.getDate("startDate"),
            rs.getDate("endDate")
        ), clientID);
    }

    @Override
    /**
     * @param comparator ">", "<", "=", "<=", ">="
     * @param comparingDate Date to compare with or null to do no comparissons
     */
    public List<SignatureEntity> getSignatureByEndDate(String comparator, Date comparingDate) {
        String sql = "SELECT * FROM signatures WHERE endDate " + comparator + " ?";
        return database.query(sql, (rs, rowNum) -> new SignatureEntity(
            rs.getLong("id"),
            rs.getLong("appId"),
            rs.getLong("clientId"),
            rs.getDate("startDate"),
            rs.getDate("endDate")
        ), comparingDate);
    }

    @Override
    public void updateSignature(Long signatureId, Date endDate) {
        String sql = "UPDATE signatures SET endDate = ? WHERE id = ?";
        database.update(sql, endDate, signatureId);
    }
}
