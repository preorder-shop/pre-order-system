package com.example.reservation.controller;


import com.example.reservation.common.response.BaseResponse;
import com.example.reservation.dto.GetFeedRes;
import com.example.reservation.service.FeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
@RestController
public class FeedController {

    private FeedService feedService;

    @GetMapping("")
    public BaseResponse<List<GetFeedRes>> getFeedList(@RequestHeader("Authorization") String authorizationHeader){

        String jwtToken = authorizationHeader.substring(7);
        List<GetFeedRes> feedList = feedService.getFeedList(jwtToken);

        return new BaseResponse<>(feedList);
    }

}
