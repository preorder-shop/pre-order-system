package com.example.reservation.controller;

import com.example.reservation.dto.EmailCertificationReq;
import com.example.reservation.dto.SignUpReq;
import com.example.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    // 이메일 인증
    @PostMapping("/email-certification")
    public String emailCertificate(@RequestBody EmailCertificationReq emailCertificationReq){

        String result = userService.emailCertificate(emailCertificationReq);

        return result;
    }


    // ==== 변경 관련 ====

    // 사용자 정보 변경

    @PatchMapping("")
    public String patchUserInfo(){

        return "사용자 정보를 변경하였습니다.";
    }


    // 비밀번호 변경
    @PatchMapping("/password")
    public String patchUserPassword(){

        return "비밀번호 변경을 완료했습니다.";

    }
}
