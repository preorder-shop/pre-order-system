package com.example.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf((auth)->auth.disable());

        httpSecurity
                .formLogin((auth)->auth.disable());

        httpSecurity
                .httpBasic((auth)->auth.disable());

        // 인증 인가 관련
        httpSecurity
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers("/app/v1/users/email-certification","/main","/test","/app/v1/users/login","/app/v1/users/signup").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );


                return httpSecurity.build();

    }


}
