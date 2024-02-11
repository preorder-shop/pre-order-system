package com.example.activity_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetNewsFeedReq {

    private String userId;
    private String type;
    private String sort;
    private int startPage;
}
