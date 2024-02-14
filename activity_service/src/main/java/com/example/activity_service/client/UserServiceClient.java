package com.example.activity_service.client;

import com.example.activity_service.dto.request.ValidateRefreshTokenReq;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user")
public interface UserServiceClient {

    @PostMapping("/users/internal/token")
    void validateRefreshToken(@RequestBody ValidateRefreshTokenReq validateRefreshTokenReq);

    @GetMapping("/users/internal/{userId}")
    void validateUserId(@PathVariable String userId);



}
