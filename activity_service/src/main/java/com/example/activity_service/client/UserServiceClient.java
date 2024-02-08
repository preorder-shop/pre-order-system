package com.example.activity_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserServiceClient {

    // 유저 서비스에서 토큰에 대한 정보를
    @GetMapping ("/users/internal/token")
    void validateRefreshToken();

    @GetMapping("/users/internal/{userId}")
    void validateUserId(@PathVariable String userId);


}
