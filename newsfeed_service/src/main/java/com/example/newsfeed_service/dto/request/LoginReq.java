package com.example.newsfeed_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginReq {

    private String email;
    private String password;
}
