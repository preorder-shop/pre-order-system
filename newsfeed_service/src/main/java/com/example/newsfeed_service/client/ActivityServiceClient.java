package com.example.newsfeed_service.client;

import com.example.newsfeed_service.dto.request.GetNewsFeedReq;
import com.example.newsfeed_service.dto.response.NewsFeedDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "activity")
public interface ActivityServiceClient {

    @PostMapping("/activity/internal/newsfeed/post")
    List<NewsFeedDto> getPostListByCondition(@RequestBody GetNewsFeedReq getNewsFeedReq);

}
