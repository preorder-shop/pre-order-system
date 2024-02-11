package com.example.activity_service.common.config;

import com.example.activity_service.repository.UserLogJdbcTemplateRepository;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JdbcConfig {

    private final DataSource dataSource;

    @Bean
    public UserLogJdbcTemplateRepository userLogJdbcTemplateRepository(){
        return new UserLogJdbcTemplateRepository(dataSource);
    }

}
