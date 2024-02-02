package com.example.user_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchUserInfoReq {

    private String name; // 유저이름
    private String greeting; // 인사말

}
