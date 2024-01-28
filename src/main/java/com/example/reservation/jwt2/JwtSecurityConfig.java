package com.example.reservation.jwt2;

import com.example.reservation.jwt.JWTFilter;
import com.example.reservation.jwt.JWTUtil;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
{

    private JWTUtil jwtUtil;
    public JwtSecurityConfig(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder
                .addFilterBefore(
                        new JWTFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class
                );
    }
}
