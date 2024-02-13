package com.example.activity_service.controller;

import com.example.activity_service.common.jwt.JWTUtil;
import com.example.activity_service.common.response.BaseResponse;
import com.example.activity_service.dto.response.GetFollowerRes;
import com.example.activity_service.dto.response.UserDto;
import com.example.activity_service.service.FollowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity/follow")
@RestController
public class FollowController {

    private final FollowService followService;
    private final JWTUtil jwtUtil;

    @GetMapping("/{followId}")
    public BaseResponse<String> followOther( @PathVariable(name = "followId") String followId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        System.out.println("현재 팔로우를 요청한 사람");
        System.out.println(userId);

        System.out.println("팔로우 신청을 받은 사람");
        System.out.println(followId);

        String result = followService.followOther(userId, followId);

        return new BaseResponse<>(result);
    }


}
