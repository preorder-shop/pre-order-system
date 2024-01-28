package com.example.reservation.jwt;

import com.example.reservation.dto.CustomUserDetails;
import com.example.reservation.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter { // JWT 검증 필터 -> 헤더로 들어온 jwt 토큰을 검증

    private static final String[] whileLists = {"/api/v1/users/login","/api/v1/users/logout"}; // jwt 검증을 해야되는 경로 저장..
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청값에 헤더에서 jwt token 꺼냄.
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        String requestURI = request.getRequestURI(); // 요청한 경로

        String refreshToken = "";

        // 헤더에 들어있는 쿠키 정보 출력
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            System.out.println("==================");
            System.out.println("쿠키 정보 확인");
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                System.out.println(cookie.getValue());
                if(Objects.equals(cookie.getName(), "refreshToken")){
                    refreshToken = cookie.getValue();
                }
            }
            System.out.println("==================");
            System.out.println("요청 url 확인");
            System.out.println(requestURI);
        }


        // 로그인 권한이 필요 없는 경로 처리
        if(Objects.equals(requestURI, "/api/v1/users/login")){
            filterChain.doFilter(request,response);
            return;
        }


//        if(checkIsLoginPath(requestURI)||){
//
//
//            System.out.println("로그인할때는 jwt 검증할 필요 없음.");
//            filterChain.doFilter(request,response);
//            return;
//
//        }



        if(authorization==null || !authorization.startsWith("Bearer ")){
            System.out.println("헤더에 토큰이 없음 !");
            filterChain.doFilter(request,response); //  다음 필터 or 서블릿 호출(DispatcherServlet)
            return;
        }

        System.out.println("해더에서 토큰 발견");
        String accessToken = authorization.split(" ")[1];

        // 이미 만료된 토큰인지 확인
//        if(jwtUtil.isExpired(token)){
//            System.out.println("만료된 토큰 ");
//            filterChain.doFilter(request,response); //  다음 필터 or 서블릿 호출(DispatcherServlet)
//            return;
//        }

        // 토큰이 있을때.
        // Todo
        // todo : 1. refreshToken 꺼냄.
        // todo 2 :
        if(StringUtils.hasText(accessToken)&& !jwtUtil.isExpired(accessToken)){ // AccessToken 이 존재하고 유효한 경우
            System.out.println("해당 ACCESSTOKEN 은 유효함 -> 인증 ok.");
            // 인가 처리
            String email = jwtUtil.getEmail(accessToken);
            String role = jwtUtil.getRole(accessToken);
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

        }else if(StringUtils.hasText(accessToken) && jwtUtil.isExpired(accessToken)){ // AccessToken 이 존재하지만 만료된 경우
            System.out.println("해당 ACCESSTOKEN은 만료됬음.");





            if(StringUtils.hasText(refreshToken)) { // RefreshToken 이 존재하면
                if (!jwtUtil.isExpired(refreshToken)) { // RefreshToken 이 유효하면
                    // todo : access 토큰 새로 발급.
                    String email = jwtUtil.getEmail(refreshToken);
                    String role = jwtUtil.getRole(refreshToken);
                    String newRefresh = jwtUtil.createToken(email, role, "REFRESH");
                    String newAccess = jwtUtil.createToken(email,role,"ACCESS");
                    response.addHeader("Authorization", "Bearer " + newAccess);
                    jwtUtil.addRefreshTokenInCookie(newRefresh,response);

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
                } else {
                    // todo : 인가 거절
                    System.out.println("인가 거절 : 재로그인 필요");
                    response.sendRedirect("/api/v1/users/login");
                }

            }else{ // Refresh Token 이 존재하지 않음.
                // todo : 거절
                System.out.println("인가 거절 : 재로그인 필요");
                response.sendRedirect("/api/v1/users/login");

            }
        }else{ // AccessToken 없음
            if(StringUtils.hasText(refreshToken)) { // RefreshToken 이 존재하면
                if (!jwtUtil.isExpired(refreshToken)) { // RefreshToken 이 유효하면
                    // todo : access 토큰 새로 발급.
                    String email = jwtUtil.getEmail(refreshToken);
                    String role = jwtUtil.getRole(refreshToken);
                    String newRefresh = jwtUtil.createToken(email, role, "REFRESH");
                    String newAccess = jwtUtil.createToken(email,role,"ACCESS");
                    response.addHeader("Authorization", "Bearer " + newAccess);
                    jwtUtil.addRefreshTokenInCookie(newRefresh,response);

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
                } else {
                    // todo : 인가 거절
                    System.out.println("인가 거절 : 재로그인 필요");
                    response.sendRedirect("/api/v1/users/login");
                }

            }else{ // Refresh Token 이 존재하지 않음.
                // todo : 거절
                System.out.println("인가 거절 : 재로그인 필요");
                response.sendRedirect("/api/v1/users/login");

            }

        }





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
       filterChain.doFilter(request,response);


    }

    private boolean checkIsLoginPath(String requestURI){ // 로그인을 해야만 하는 경로 (jwt, 인가가 필요한 경로인지 확인)
        return !PatternMatchUtils.simpleMatch(whileLists,requestURI);
    }
}
