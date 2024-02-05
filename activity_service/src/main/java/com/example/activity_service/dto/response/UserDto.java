package com.example.activity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String greeting;
    private String profileImg;

//    @Builder
//    public UserDto(Long id,String name,String greeting,String profileImg){
//        this.id = id;
//        this.name = name;
//        this.greeting = greeting;
//        this.profileImg = profileImg;
//    }



}
