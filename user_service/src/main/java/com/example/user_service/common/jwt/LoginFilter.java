package com.example.user_service.common.jwt;

import com.example.user_service.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager; // DB를 통해서 유저정보를 가져와서 로그인한 데이터와 검증
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // todo : 입력 값에 대한 형식적 validation 처리
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);\
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 인증 객체에 저장
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email
                ,password,null);

        return authenticationManager.authenticate(authenticationToken);


    }

    @Override // 검증 성공시 실행 함수
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
       log.error("로그인 필터를 통한 검증 로그인 성공 !!");
       //  JWT 발급
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

      //  String token = jwtUtil.createJwt(username, role, 60*60*10L);
        String accessToken = jwtUtil.createToken(username,role,"ACCESS");
        String refreshToken = jwtUtil.createToken(username,role,"REFRESH");

        // JWT 는 header 에 담아서 return
        response.addHeader("Authorization", "Bearer " + accessToken);
      //  response.addHeader("Refresh", "Bearer " + refreshToken);
//        Cookie cookie = new Cookie("refreshToken",refreshToken);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);

        // refresh token 은 쿠키에 담아서 전송
        jwtUtil.addRefreshTokenInCookie(refreshToken,response);

    }

    @Override // 검증 실패시 실행 함수
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.error("로그인 필터를 통한 로그인 실패 !!");
        response.setStatus(401);
    }
}
