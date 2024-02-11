package com.example.newsfeed_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class GetNewsFeedReq {

    private String userId;
    private String type;
    private String sort;
    private int startPage;
}
