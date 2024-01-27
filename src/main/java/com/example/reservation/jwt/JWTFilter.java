package com.example.reservation.jwt;

import com.example.reservation.dto.CustomUserDetails;
import com.example.reservation.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter { // JWT 검증 필터

    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청값에 헤더에서 jwt token 꺼냄.
        String authorization = request.getHeader("Authorization");

        if(authorization==null || !authorization.startsWith("Bearer ")){
            System.out.println("token null");
            filterChain.doFilter(request,response); //  다음 필터 or 서블릿 호출(DispatcherServlet)

            return;
        }

        System.out.println("authorization now");
        String token = authorization.split(" ")[1];

        // 이미 만료된 토큰인지 확인
        if(jwtUtil.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request,response); //  다음 필터 or 서블릿 호출(DispatcherServlet)

            return;
        }

        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        User user = User.builder()
                .email(email)
                .password("temppassword")
                .role(role)
                .build();


        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);


    }
}
