package com.example.activity_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchPasswordReq {

    @NotBlank
    private String password;
}
