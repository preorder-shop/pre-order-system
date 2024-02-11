package com.example.newsfeed_service.client;

import com.example.newsfeed_service.dto.request.ValidateRefreshTokenReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user")
public interface UserServiceClient {

    @GetMapping("/users/internal/token")
    void validateRefreshToken(@RequestBody ValidateRefreshTokenReq validateRefreshTokenReq);

    @GetMapping("/users/internal/{userId}")
    void validateUserId(@PathVariable String userId);

}
