package com.projarq.scaa.frameworksDrivers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.scaa.interfaceAdaptors.repository.IUserRepository;

@Repository
public class UserDatabase implements IUserRepository {
    private JdbcTemplate database;

    @Autowired
    public UserDatabase(JdbcTemplate database) {
        this.database = database;
    }
}
