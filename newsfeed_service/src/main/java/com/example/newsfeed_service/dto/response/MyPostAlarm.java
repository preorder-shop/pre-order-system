package com.example.newsfeed_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class MyPostAlarm {
    private String postId; //
    private String actor; // 행위자
    private String activeType; // 활동 내용


}
