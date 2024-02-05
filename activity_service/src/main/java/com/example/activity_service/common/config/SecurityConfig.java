//package com.example.activity_service.common.config;
//
//import com.example.activity_service.common.jwt.JWTFilter;
//import com.example.activity_service.common.jwt.JWTUtil;
//import com.example.activity_service.common.jwt.LoginFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final AuthenticationConfiguration authenticationConfiguration;
//    private final JWTUtil jwtUtil;
//
//    private final JWTFilter jwtFilter;
//
//    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil
//                          ,JWTFilter jwtFilter
//    ) {
//
//        this.authenticationConfiguration = authenticationConfiguration;
//        this.jwtUtil = jwtUtil;
//        this.jwtFilter = jwtFilter;
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    //AuthenticationManager Bean 등록
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//
//        return configuration.getAuthenticationManager();
//    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//                .csrf((auth) -> auth.disable());
//
//        httpSecurity
//                .formLogin((auth) -> auth.disable());
//
//        httpSecurity
//                .httpBasic((auth) -> auth.disable());
//
//        httpSecurity
//                .formLogin((auth) -> auth.disable());
//
//        // 인증 인가 관련
//        httpSecurity
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/api/v1/users/signup", "/api/v1/users/email-certification",
//                                "/api/v1/users/login", "/login","/main"
//                        ).permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                );
//
////
////        httpSecurity
////                .addFilterBefore(new JWTFilter(jwtUtil,tokenService), LoginFilter.class);
//
//
//        httpSecurity
//                .addFilterBefore(jwtFilter,LoginFilter.class);
//        httpSecurity
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
//                        UsernamePasswordAuthenticationFilter.class);
//
//        return httpSecurity.build();
//
//    }
//
//    @Bean
//    public FilterRegistrationBean<JWTFilter> jwtFilterFilterRegistrationBean(){
//        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(jwtFilter);
//        registrationBean.addUrlPatterns("/api/*"); // 필터를 어떤 URL에 적용할지 지정
//        return registrationBean;
//    }
//
//
//
//}
