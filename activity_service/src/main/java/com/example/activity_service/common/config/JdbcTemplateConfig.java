package com.example.activity_service.common.config;

import com.example.activity_service.repository.JdbcRepository;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {

    private final DataSource dataSource;

    @Bean
    public JdbcRepository jdbcRepository(){
        return new JdbcRepository(dataSource);
    }
}
