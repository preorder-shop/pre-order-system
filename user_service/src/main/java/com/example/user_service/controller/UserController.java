package com.example.user_service.controller;

import static com.example.user_service.common.response.BaseResponseStatus.*;

import com.example.user_service.common.exceptions.BaseException;
import com.example.user_service.common.jwt.JWTUtil;
import com.example.user_service.common.response.BaseResponse;
import com.example.user_service.dto.request.EmailCertificationReq;
import com.example.user_service.dto.request.LoginReq;
import com.example.user_service.dto.request.PatchPasswordReq;
import com.example.user_service.dto.request.SignUpReq;
import com.example.user_service.dto.response.SignUpRes;
import com.example.user_service.dto.response.UserDto;
import com.example.user_service.service.S3Service;
import com.example.user_service.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // DB를 통해서 유저정보를 가져와서 로그인한 데이터와 검증
    private final S3Service s3Service;

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

    @GetMapping("")
    public BaseResponse<UserDto> getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        UserDto userDto = userService.getUserInfo(userEmail);

        return new BaseResponse<>(userDto);

    }

    // 이메일 인증
    @PostMapping("/email-certification")
    public BaseResponse<String> emailCertificate(@RequestBody EmailCertificationReq emailCertificationReq){

        String result = userService.emailCertificate(emailCertificationReq);

        return new BaseResponse<>(result);
    }


    // ==== 변경 관련 ====

    @PatchMapping("")
    public BaseResponse<String> patchUserInfo(MultipartFile profileImage,String name,String greeting){
//        checkUsernameValidation(name);
//        checkGreetingValidation(greeting);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        String img_url=null;

        if(!profileImage.isEmpty()){
            img_url = s3Service.uploadImage(profileImage);
        }

       String result = userService.patchUserInfo(userEmail, name, greeting, img_url);

        return new BaseResponse<>(result);
    }


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
