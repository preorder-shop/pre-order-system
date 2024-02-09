package com.example.user_service.controller;

import com.example.user_service.dto.request.ValidateRefreshTokenReq;
import com.example.user_service.service.TokenService;
import com.example.user_service.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.Validate;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/internal")
public class InternalController {

    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/{userId}") // 내부적으로 사용
    public void validateUserId(@PathVariable String userId){
        log.error("user 서비스의 validateUserId 함수 실행!!!!");
        userService.validateUserId(userId);
        return;
    }

    @GetMapping("/token") // 내부적으로 사용
    public void validateRefreshToken(@RequestBody ValidateRefreshTokenReq validateRefreshTokenReq){

    //    String refreshTokenInCooke = getRefreshTokenInCookie(request);

        tokenService.validateRefreshToken(validateRefreshTokenReq.getRefreshToken(), validateRefreshTokenReq.getUserId());
    }

//    private String getRefreshTokenInCookie(HttpServletRequest request){
//        String refreshToken="";
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//
//            if(cookie.getName().equals("refreshToken")){
//                refreshToken= cookie.getValue();
//                System.out.println("내부 통신을 통해 유저 서비스를 호출해서 리프레시 토큰의 유효성 검사 진행");
//                System.out.println(refreshToken);
//                break;
//            }
//        }
//        return refreshToken;
//

   // }

}
