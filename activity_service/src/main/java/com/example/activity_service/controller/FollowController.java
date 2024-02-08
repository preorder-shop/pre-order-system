package com.example.activity_service.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    @GetMapping("/{userId}")
    public BaseResponse<String> followOther(@PathVariable(name = "userId") String followId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        String result = followService.followOther(userId, followId);

        return new BaseResponse<>(result);
    }

    @GetMapping("/{userId}")
    public List<GetFollowerRes> getFollowers(@PathVariable(name="userId") Long userId){ // 나를 팔로우하는 사람들 목록

        List<GetFollowerRes> result = followService.getFollowers(userId);
        return result;
    }

}
