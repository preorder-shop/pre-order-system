package com.example.reservation.controller;


import com.example.reservation.response.BaseResponse;
import com.example.reservation.dto.response.GetFeedRes;
import com.example.reservation.service.FeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
@RestController
public class FeedController {

    private final FeedService feedService;

    @GetMapping("")
    public BaseResponse<List<GetFeedRes>> getFeedList(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        List<GetFeedRes> feedList = feedService.getFeedList(userEmail);

        return new BaseResponse<>(feedList);
    }


}
