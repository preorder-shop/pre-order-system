package com.example.activity_service.controller;

import com.example.activity_service.dto.response.GetFollowerRes;
import com.example.activity_service.service.FollowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/activity/internal")
public class InternalController {

    private final FollowService followService;
    @GetMapping("follow/{userId}")
    public List<GetFollowerRes> getFollowers(@PathVariable(name="userId") String userId){ // 나를 팔로우하는 사람들 목록

        List<GetFollowerRes> result = followService.getFollowers(userId);
        return result;
    }
}
