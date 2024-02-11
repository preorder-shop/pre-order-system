package com.example.activity_service.repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class UserLogJdbcTemplateRepository {

    private final NamedParameterJdbcTemplate template;

    public UserLogJdbcTemplateRepository(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }
}
