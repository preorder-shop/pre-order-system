package com.example.reservation.dto;

import com.example.reservation.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpReq {

    private String name;
    private String email;
    private String password;

    public User toEntity(String encryptedPw){
        return User
                .builder()
                .name(this.name)
                .email(this.email)
                .password(encryptedPw)
                .build();
    }



}
