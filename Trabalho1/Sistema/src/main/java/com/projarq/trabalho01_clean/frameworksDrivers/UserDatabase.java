package com.projarq.trabalho01_clean.frameworksDrivers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.trabalho01_clean.interfaceAdaptors.repository.IUserRepository;

@Repository
public class UserDatabase implements IUserRepository {
    private JdbcTemplate database;

    @Autowired
    public UserDatabase(JdbcTemplate database) {
        this.database = database;
    }
}
