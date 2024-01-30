package com.example.reservation.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Component
public class JWTUtil { // JWT 생성

    private final SecretKey secretKey;
    private final long accessTokenValidMs;
    private final long refreshTokenValidMs;

    public JWTUtil(@Value("${jwt.secret}")String secret,
                   @Value("${jwt.token-valid-in-seconds}") long tokenValidInSeconds) { // 생성자를 주입받음.

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenValidMs =tokenValidInSeconds*1000; // 24 hours -> 추후 30분으로 변경
        this.refreshTokenValidMs = tokenValidInSeconds*1000*30; // 30 days (한달)

    }

    // 검증을 진행할 3개의 메서드

    public String getEmail(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Date getExpiredDate(String token){
         return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
    }

    // JWT 토큰을 생성할 메서드
//    public String createJwt(String username, String role, Long expiredMs) {
//
//        return Jwts.builder()
//                .claim("email", username)
//                .claim("role", role)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + expiredMs))
//                .signWith(secretKey)
//                .compact();
//    }

    public String createToken(String email,String role,String type){
        long now = (new Date()).getTime();
        if(Objects.equals(type, "ACCESS")){
            now +=this.accessTokenValidMs;
            Date validity = new Date(now);
            return Jwts.builder()
                    .claim("email",email)
                    .claim("role",role)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(validity)
                    .signWith(secretKey)
                    .compact();

        }
        if(Objects.equals(type, "REFRESH")){
            now +=this.refreshTokenValidMs;
            Date validity = new Date(now);
            return Jwts.builder()
                    .claim("email",email)
                    .claim("role",role)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(validity)
                    .signWith(secretKey)
                    .compact();
        }
        return null;
    }


    public void addRefreshTokenInCookie(String refreshToken, HttpServletResponse response){
        Cookie cookie = new Cookie("refreshToken",refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
    }

}