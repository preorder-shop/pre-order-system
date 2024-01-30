package com.example.reservation.jwt;

import com.example.reservation.dto.CustomUserDetails;
import com.example.reservation.entity.Token;
import com.example.reservation.entity.User;
import com.example.reservation.repository.TokenRepository;
import com.example.reservation.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import jdk.jshell.Snippet.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter { // JWT 검증 필터 -> 헤더로 들어온 jwt 토큰을 검증

    private static final String[] whileLists = {"/api/v1/users/login", "/api/v1/users/logout"}; // jwt 검증을 해야되는 경로 저장..
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JWTUtil jwtUtil;

    //   private final TokenRepository tokenRepository;

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청값에 헤더에서 jwt token 꺼냄.
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        String requestURI = request.getRequestURI(); // 요청한 경로

        System.out.println("어서라이제이션값 확인");
        System.out.println(authorization);

        String refreshToken = "";

        // 헤더에 들어있는 쿠키 정보 출력
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println("==================");
            System.out.println("쿠키 정보 확인");
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                System.out.println(cookie.getValue());
                if (Objects.equals(cookie.getName(), "refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
            System.out.println("==================");
            System.out.println("요청 url 확인");
            System.out.println(requestURI);
        }

        // 로그인 권한이 필요 없는 경로 처리
        if (Objects.equals(requestURI, "/api/v1/users/login") || Objects.equals(requestURI,
                "/api/v1/users/email-certification") || Objects.equals(requestURI, "/api/v1/users/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean validate = false;

        if (authorization == null || !authorization.startsWith("Bearer ")) { // 헤더에 access token 값이 없을때
            System.out.println("access token 값이 null거나 Bearer 에 해당하는 토큰 값이 없음");

            validate = validateRefreshToken(refreshToken);
            if (!validate) {
                System.out.println("로그인 화면으로 redirect");
              //  response.sendRedirect("/api/v1/users/login");
                response.sendError(404,"다시 로그인 해주세요.");
                filterChain.doFilter(request,response);
                return;
            }
            String email = jwtUtil.getEmail(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            tokenService.validateRefreshToken(refreshToken, email);

            authenticateUser(email, role);
            issuedNewAccessToken(response, email, role);
            filterChain.doFilter(request, response); // 다음 필터로

            System.out.println("refresh token 확인하고 access token 새로 발급함.");
            return;
        }

        String accessToken = authorization.split(" ")[1];

        if (jwtUtil.isExpired(accessToken)) {   // 헤더에 access token 값은 있지만 만료되었을때 -> refresh token 확인
            System.out.println("access token 값이 만료되었음. refresh token 확인에정");
            // refresh token 확인
            validate = validateRefreshToken(refreshToken);
            if (!validate) {
                System.out.println("로그인 화면으로 redirect");
                response.sendRedirect("/api/v1/users/login");
                System.out.println("리프래시 토큰에 문제있음.");
                return;
            }
            String email = jwtUtil.getEmail(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            tokenService.validateRefreshToken(refreshToken, email);

            authenticateUser(email, role);
            issuedNewAccessToken(response, email, role);
            filterChain.doFilter(request, response);

            System.out.println("refresh token 확인하고 access token 새로 발급함.");
            return;

        }

        System.out.println("access token 이 유효하므로 인가 처리함.");

        // access token 이 유효한 경우 -> 인가 처리
        String email = jwtUtil.getEmail(accessToken);
        String role = jwtUtil.getRole(accessToken);
        authenticateUser(email, role);

        filterChain.doFilter(request, response);

//        if(StringUtils.hasText(accessToken)&& !jwtUtil.isExpired(accessToken)){ // AccessToken 이 존재하고 유효한 경우
//            System.out.println("해당 ACCESSTOKEN 은 유효함 -> 인증 ok.");
//            // 인가 처리
//            String email = jwtUtil.getEmail(accessToken);
//            String role = jwtUtil.getRole(accessToken);
//            User user = User.builder()
//                    .email(email)
//                    .password("temppassword")
//                    .role(role)
//                    .build();
//
//            // UserDetails에 회원 정보 객체 담기
//            CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//            // 스프링 시큐리티 인증 토큰 생성
//            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
//                    customUserDetails.getAuthorities());
//            // 세션에 사용자 등록
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        }else if(StringUtils.hasText(accessToken) && jwtUtil.isExpired(accessToken)){ // AccessToken 이 존재하지만 만료된 경우
//            System.out.println("해당 ACCESSTOKEN은 만료됬음.");
//
//
//
//
//            if(StringUtils.hasText(refreshToken)) { // RefreshToken 이 존재하면
//                if (!jwtUtil.isExpired(refreshToken)) { // RefreshToken 이 유효하면
//                    // todo : access 토큰 새로 발급.
//                    String email = jwtUtil.getEmail(refreshToken);
//                    String role = jwtUtil.getRole(refreshToken);
//                    String newRefresh = jwtUtil.createToken(email, role, "REFRESH");
//                    String newAccess = jwtUtil.createToken(email,role,"ACCESS");
//                    response.addHeader("Authorization", "Bearer " + newAccess);
//                    jwtUtil.addRefreshTokenInCookie(newRefresh,response);
//
//                    User user = User.builder()
//                            .email(email)
//                            .password("temppassword")
//                            .role(role)
//                            .build();
//
//                    // UserDetails에 회원 정보 객체 담기
//                    CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//                    // 스프링 시큐리티 인증 토큰 생성
//                    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
//                            customUserDetails.getAuthorities());
//                    // 세션에 사용자 등록
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                } else {
//                    // todo : 인가 거절
//                    System.out.println("인가 거절 : 재로그인 필요");
//                    response.sendRedirect("/api/v1/users/login");
//                }
//
//            }else{ // Refresh Token 이 존재하지 않음.
//                // todo : 거절
//                System.out.println("인가 거절 : 재로그인 필요");
//                response.sendRedirect("/api/v1/users/login");
//
//            }
//        }else{ // AccessToken 없음
//            if(StringUtils.hasText(refreshToken)) { // RefreshToken 이 존재하면
//                if (!jwtUtil.isExpired(refreshToken)) { // RefreshToken 이 유효하면
//                    // todo : access 토큰 새로 발급.
//                    String email = jwtUtil.getEmail(refreshToken);
//                    String role = jwtUtil.getRole(refreshToken);
//                    String newRefresh = jwtUtil.createToken(email, role, "REFRESH");
//                    String newAccess = jwtUtil.createToken(email,role,"ACCESS");
//                    response.addHeader("Authorization", "Bearer " + newAccess);
//                    jwtUtil.addRefreshTokenInCookie(newRefresh,response);
//
//                    User user = User.builder()
//                            .email(email)
//                            .password("temppassword")
//                            .role(role)
//                            .build();
//
//                    // UserDetails에 회원 정보 객체 담기
//                    CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//                    // 스프링 시큐리티 인증 토큰 생성
//                    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
//                            customUserDetails.getAuthorities());
//                    // 세션에 사용자 등록
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                } else {
//                    // todo : 인가 거절
//                    System.out.println("인가 거절 : 재로그인 필요");
//                    response.sendRedirect("/api/v1/users/login");
//                }
//
//            }else{ // Refresh Token 이 존재하지 않음.
//                // todo : 거절
//                System.out.println("인가 거절 : 재로그인 필요");
//                response.sendRedirect("/api/v1/users/login");
//
//            }

        //    }

//        String email = jwtUtil.getEmail(token);
//        String role = jwtUtil.getRole(token);
//
//        User user = User.builder()
//                .email(email)
//                .password("temppassword")
//                .role(role)
//                .build();
//
//
//        // UserDetails에 회원 정보 객체 담기
//        CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//        // 스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//        // 세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//

    }

    private boolean checkIsLoginPath(String requestURI) { // 로그인을 해야만 하는 경로 (jwt, 인가가 필요한 경로인지 확인)
        return !PatternMatchUtils.simpleMatch(whileLists, requestURI);
    }

    private boolean validateRefreshToken(String refreshToken) {

        if (Objects.equals(refreshToken, "")) {
            return false;
        }
        if (jwtUtil.isExpired(refreshToken)) {
            return false;
        }

        return true;
    }

    private void issuedNewAccessToken(HttpServletResponse response, String email, String role) {

        String newAccess = jwtUtil.createToken(email, role, "ACCESS");
        response.addHeader("Authorization", "Bearer " + newAccess);

    }

    private void authenticateUser(String email, String role) {
        User user = User.builder()
                .email(email)
                .password("temppassword")
                .role(role)
                .build();

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

    }
}
