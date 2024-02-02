package com.example.newsfeed_service.controller;

import com.example.newsfeed_service.common.response.BaseResponse;
import com.example.newsfeed_service.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    @GetMapping("/{id}")
    public BaseResponse<String> followOther(@PathVariable(name = "id") Long id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        String result = followService.followOther(userEmail, id);

        return new BaseResponse<>(result);
    }

}
