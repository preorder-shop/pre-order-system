package com.example.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter @Setter
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String greeting;
    private String profileImg;




}
