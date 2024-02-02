package com.example.activity_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginReq {

    private String email;
    private String password;
}
