package com.example.newsfeed_service.controller;


import com.example.newsfeed_service.client.ActivityServiceClient;

import com.example.newsfeed_service.common.response.BaseResponse;
import com.example.newsfeed_service.dto.request.GetNewsFeedReq;
import com.example.newsfeed_service.dto.response.NewsFeedDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/feeds")
@RestController
public class FeedController {

   // private final FeedService feedService;

    private final ActivityServiceClient activityServiceClient;

    /**
     *  포스트 리스트 조회 API
     */
    @GetMapping("")
    public BaseResponse<List<NewsFeedDto>> getFeedList(HttpServletRequest request, @RequestParam(name = "type",defaultValue = "all") String type,
                                    @RequestParam(name = "sort",defaultValue = "date")String sort,
                                    @RequestParam(name = "startPage",defaultValue = "0") int startPage){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
//    //    List<GetFeedRes> feedList = feedService.getFeedList(userId);
//        activityService


   //     return new BaseResponse<>(feedList);
        System.out.println("포스트 리스트 API url 정보");
        System.out.println(request.getRequestURI());
        System.out.println(userId);
        System.out.println(type);
        System.out.println(sort);
        log.info("knk");
        // todo : 비 정상적인 값에 대한 예외처리

        GetNewsFeedReq getNewsFeedReq = new GetNewsFeedReq(userId,type,sort, startPage);
        List<NewsFeedDto> feedList = activityServiceClient.getFeedList(getNewsFeedReq);
     //   System.out.println(feedList);
        return new BaseResponse<>(feedList);
    }


 //   @GetMapping("")


}
