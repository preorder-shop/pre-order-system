package com.example.reservation.controller;

import com.example.reservation.dto.SignUpReq;
import com.example.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody SignUpReq signUpReq){

        // todo : 형식적 validation
        userService.signup(signUpReq);

        return "회원가입 완료";
    }


    // 로그인
    @PostMapping("/login")
    public String login(){
        return "로그인 완료";
    }


    // 이메일 체크


    // ==== 변경 관련 ====
}
