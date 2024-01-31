package com.example.reservation.controller;

import static com.example.reservation.common.response.BaseResponseStatus.*;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.common.response.BaseResponse;
import com.example.reservation.dto.EmailCertificationReq;
import com.example.reservation.dto.LoginReq;
import com.example.reservation.dto.PatchPasswordReq;
import com.example.reservation.dto.PatchUserInfoReq;
import com.example.reservation.dto.SignUpReq;
import com.example.reservation.dto.SignUpRes;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.jwt.LoginFilter;
import com.example.reservation.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager; // DB를 통해서 유저정보를 가져와서 로그인한 데이터와 검증

    // 회원가입
    @PostMapping("/signup")
    public BaseResponse<SignUpRes> createUser(@RequestBody SignUpReq signUpReq){

        // 형식적 validation
        checkUsernameValidation(signUpReq.getName());
        checkEmailValidation(signUpReq.getEmail());
        checkCodeValidation(signUpReq.getCode());
        checkPasswordValidation(signUpReq.getPassword());
        checkGreetingValidation(signUpReq.getGreeting());

        SignUpRes signUpRes = userService.createUser(signUpReq);

        return new BaseResponse<>(signUpRes);
    }


    // 로그인
    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginReq loginReq, HttpServletResponse response){

        checkEmailValidation(loginReq.getEmail());
        checkPasswordValidation(loginReq.getPassword());

        try{  // 유저 검증
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginReq.getEmail(),loginReq.getPassword());
            authenticationManager.authenticate(authenticationToken);

        }catch (Exception e){
            throw new BaseException(INVALID_LOGIN);
        }

        String userRole = userService.getUserRole(loginReq.getEmail());

        String accessToken = jwtUtil.createToken(loginReq.getEmail(), userRole, "ACCESS");
        String refreshToken = jwtUtil.createToken(loginReq.getEmail(), userRole, "REFRESH");


        Date expiredDate = jwtUtil.getExpiredDate(refreshToken);
        String userEmail = loginReq.getEmail();
        userService.accessTokenSave(refreshToken,userEmail,expiredDate);


        response.addHeader("Authorization", "Bearer " + accessToken);
        jwtUtil.addRefreshTokenInCookie(refreshToken,response);

        return new BaseResponse<>("로그인을 완료했습니다.");
    }

    @GetMapping("/logout")
    public BaseResponse<String> logout(HttpServletResponse response){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        userService.deleteRefreshToken(userEmail);
        response.addHeader("Authorization","");
        expireCookie(response,"refreshToken");
        return new BaseResponse<>("로그아웃을 완료했습니다.");
    }

    // 이메일 인증
    @PostMapping("/email-certification")
    public BaseResponse<String> emailCertificate(@RequestBody EmailCertificationReq emailCertificationReq){

        String result = userService.emailCertificate(emailCertificationReq);

        return new BaseResponse<>(result);
    }


    // ==== 변경 관련 ====

    // 사용자 정보 변경

//    @PatchMapping("")
//    public String patchUserInfo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PatchUserInfoReq patchUserInfoReq){
//
//        checkUsernameValidation(patchUserInfoReq.getName());
//        checkGreetingValidation(patchUserInfoReq.getGreeting());
//
//        String jwtToken = authorizationHeader.substring(7);
//        String result = userService.patchUserInfo(patchUserInfoReq, jwtToken);
//
//        return result;
//    }

    @PatchMapping("")
    public BaseResponse<String> patchUserInfo(@RequestBody PatchUserInfoReq patchUserInfoReq){

        checkUsernameValidation(patchUserInfoReq.getName());
        checkGreetingValidation(patchUserInfoReq.getGreeting());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        String result = userService.patchUserInfo(patchUserInfoReq, userEmail);

        return new BaseResponse<>(result);
    }

    // 비밀번호 변경
//    @PatchMapping("/password")
//    public String patchUserPassword(@RequestHeader("Authorization") String authorizationHeader,@RequestBody PatchPasswordReq patchPasswordReq){
//
//        // todo : 값에 대한 형식적 validation 처리
//        String jwtToken = authorizationHeader.substring(7);
//        String result = userService.patchPassword(patchPasswordReq, jwtToken);
//
//        return result;
//
//    }

    @PatchMapping("/password")
    public BaseResponse<String> patchUserPassword(@RequestBody PatchPasswordReq patchPasswordReq,HttpServletResponse response){

        checkPasswordValidation(patchPasswordReq.getPassword());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        String result = userService.patchPassword(patchPasswordReq, userEmail);
        response.addHeader("Authorization","");
        expireCookie(response,"refreshToken");

        return new BaseResponse<>(result);

    }

    private static void expireCookie(HttpServletResponse response,String name) {
        Cookie cookie=new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private void checkUsernameValidation(String name){
        if(name==null || name.isBlank())
            throw new BaseException(USERS_EMPTY_USER_NAME);

    }
    private void checkEmailValidation(String email){
        if(email==null || email.isBlank()){
            throw new BaseException(USERS_EMPTY_EMAIL);
        }

    }

    private void checkCodeValidation(String code){
        if(code==null || code.isBlank()){
            throw new BaseException(USERS_EMPTY_EMAIL_CODE);
        }

    }

    private void checkPasswordValidation(String pd){
        if(pd==null || pd.isBlank()){
            throw new BaseException(USERS_EMPTY_PASSWORD);
        }
    }

    private void checkGreetingValidation(String greeting){
        if(greeting==null || greeting.isBlank()){
            throw new BaseException(USERS_EMPTY_GREETING);
        }
    }
}
