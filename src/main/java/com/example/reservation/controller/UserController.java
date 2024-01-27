package com.example.reservation.controller;

import com.example.reservation.dto.EmailCertificationReq;
import com.example.reservation.dto.LoginReq;
import com.example.reservation.dto.PatchPasswordReq;
import com.example.reservation.dto.PatchUserInfoReq;
import com.example.reservation.dto.SignUpReq;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.jwt.LoginFilter;
import com.example.reservation.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    private final JWTUtil jwtUtil;

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody SignUpReq signUpReq){

        // todo : 형식적 validation
        String result = userService.signup(signUpReq);

        return result;
    }


    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginReq loginReq, HttpServletResponse response){

        String token = userService.login(loginReq);

        response.addHeader("Authorization", "Bearer " + token);

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
    public String patchUserInfo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PatchUserInfoReq patchUserInfoReq){

        // todo : 값에 대한 형식적 validation 처리
        String jwtToken = authorizationHeader.substring(7);
        String result = userService.patchUserInfo(patchUserInfoReq, jwtToken);

        return result;
    }


    // 비밀번호 변경
    @PatchMapping("/password")
    public String patchUserPassword(@RequestHeader("Authorization") String authorizationHeader,@RequestBody PatchPasswordReq patchPasswordReq){

        // todo : 값에 대한 형식적 validation 처리
        String jwtToken = authorizationHeader.substring(7);
        String result = userService.patchPassword(patchPasswordReq, jwtToken);

        return result;

    }
}
