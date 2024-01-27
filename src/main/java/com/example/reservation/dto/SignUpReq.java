package com.example.reservation.dto;

import com.example.reservation.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReq {
    @NotBlank
    private String email;
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    public User toEntity(String encryptedPw) {
        return User
                .builder()
                .name(this.name)
                .email(this.email)
                .password(encryptedPw)
                .role("ROLE_USER")
                .build();
    }


}
