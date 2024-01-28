package com.example.reservation.controller;

import com.example.reservation.common.response.BaseResponse;
import com.example.reservation.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
@RestController
public class FollowController {

    private final FollowService followService;

    @PostMapping ("/{id}")
    public BaseResponse<String> followOther(@RequestHeader("Authorization") String authorizationHeader, @PathVariable(name = "id") Long id){
        // 쿼리 파라미터로 팔로우할 사용자의 id 값을 받아서 해당 사용자와 현재 사용자의 관계를 follow table에 업데이트 한다.
        String jwtToken = authorizationHeader.substring(7);
        followService.followOther(jwtToken,id);

        return new BaseResponse<>("해당 사용자를 팔로우했습니다.");
    }

}
