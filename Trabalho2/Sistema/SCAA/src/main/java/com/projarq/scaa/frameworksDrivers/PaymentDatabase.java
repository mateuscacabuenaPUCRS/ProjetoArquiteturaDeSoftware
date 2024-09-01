package com.projarq.scaa.frameworksDrivers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.scaa.domain.entity.PaymentEntity;
import com.projarq.scaa.interfaceAdaptors.repository.IPaymentRepository;

import java.util.Date;

@Repository
public class PaymentDatabase implements IPaymentRepository {
    private JdbcTemplate database;

    @Autowired
    public PaymentDatabase(JdbcTemplate database) {
        this.database = database;
    }

    @Override
    public PaymentEntity create(
        Long signatureId,
        float payedValue,
        Date paymentDate,
        String promotion
    ) {
        String sql = "INSERT INTO payments (signatureId, payedValue, paymentDate, promotion) VALUES (?, ?, ?, ?)";
        int paymentDBId = database.update(sql, signatureId, payedValue, paymentDate, promotion);
        Long paymentId = Long.valueOf(paymentDBId);
        return new PaymentEntity(paymentId, signatureId, payedValue, paymentDate);
    }

    @Override
    public List<PaymentEntity> getAll() {
        String sql = "SELECT * FROM payments";
        return database.query(sql, (rs, rowNum) -> {
            Long paymentId = rs.getLong("id");
            Long signatureId = rs.getLong("signatureId");
            float payedValue = rs.getFloat("payedValue");
            Date paymentDate = rs.getDate("paymentDate");
            return new PaymentEntity(paymentId, signatureId, payedValue, paymentDate);
        });
    }
}
