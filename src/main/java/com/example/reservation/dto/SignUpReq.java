package com.example.reservation.dto;

import com.example.reservation.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReq {

    private String email;

    private String code;
    private String name;

    private String password;
    private String greeting;

    public User toEntity(String encryptedPw) {
        return User
                .builder()
                .name(this.name)
                .email(this.email)
                .password(encryptedPw)
                .role("ROLE_USER")
                .greeting(greeting)
                .build();
    }


}
