package com.example.newsfeed_service.controller;


import static com.example.newsfeed_service.common.response.BaseResponseStatus.INVALID_REQUEST;

import com.example.newsfeed_service.client.ActivityServiceClient;

import com.example.newsfeed_service.common.exceptions.BaseException;
import com.example.newsfeed_service.common.response.BaseResponse;
import com.example.newsfeed_service.dto.request.GetNewsFeedReq;
import com.example.newsfeed_service.dto.response.MyPostAlarm;
import com.example.newsfeed_service.dto.response.NewsFeedActivityDto;
import com.example.newsfeed_service.dto.response.NewsFeedDto;
import com.example.newsfeed_service.dto.response.UserLogDto;
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

    private final ActivityServiceClient activityServiceClient;

    /**
     * 포스트 리스트 조회 API
     */
    @GetMapping("/post")
    public BaseResponse<List<NewsFeedDto>> getPostListByCondition(HttpServletRequest request,
                                                                  @RequestParam(name = "type", defaultValue = "all") String type,
                                                                  @RequestParam(name = "sort", defaultValue = "date") String sort,
                                                                  @RequestParam(name = "startPage", defaultValue = "0") int startPage) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        if (!sort.equals("date") && !sort.equals("like")) {
            throw new BaseException(INVALID_REQUEST);
        }

        if (!type.equals("all") && !type.equals("follow")) {
            throw new BaseException(INVALID_REQUEST);
        }

        GetNewsFeedReq getNewsFeedReq = new GetNewsFeedReq(userId, type, sort, startPage);
        List<NewsFeedDto> feedList = activityServiceClient.getPostListByCondition(getNewsFeedReq);

        return new BaseResponse<>(feedList);
    }

    /**
     * 내가 팔로우한 사용자의 활동
     */
    @GetMapping("/my-following")
    public BaseResponse<List<UserLogDto>> getMyFollowingActivity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        List<UserLogDto> result = activityServiceClient.getMyFollowingActivity(userId);

        return new BaseResponse<>(result);
    }

    /**
     * 나를 팔로우하는 사용자의 활동
     */
    @GetMapping("/my-follower")
    public BaseResponse<List<UserLogDto>> getMyFollowerActivity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        List<UserLogDto> result = activityServiceClient.getMyFollowerActivity(userId);
        return new BaseResponse<>(result);

    }

    /**
     * 내 포스트 알림 (댓글 , 좋아요)
     */
    @GetMapping("/my-post-alarm")
    public BaseResponse<List<MyPostAlarm>> getMyPostAlarm(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        List<MyPostAlarm> result = activityServiceClient.getMyPostAlarm(userId);

        return new BaseResponse<>(result);



    }



}
