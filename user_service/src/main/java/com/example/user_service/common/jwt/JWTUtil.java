package com.example.user_service.common.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JWTUtil { // JWT 생성

    private final SecretKey secretKey;
    private final long accessTokenValidMs;
    private final long refreshTokenValidMs;

    public JWTUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.token-valid-in-seconds}") long tokenValidInSeconds) { // 생성자를 주입받음.

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenValidMs = tokenValidInSeconds * 1000 / 24; // 1 hour
        this.refreshTokenValidMs = tokenValidInSeconds * 1000 * 1; // 1 days (하루) -> 테스트를 위해

    }

    // 검증을 진행할 3개의 메서드

    public String getUserId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("userId", String.class);
    }

    public String getEmail(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("email", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
                .before(new Date());
    }

    public Date getExpiredDate(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
    }


    public String createToken(String userId, String role, String type) {
        long now = (new Date()).getTime();
        if (Objects.equals(type, "ACCESS")) {
            now += this.accessTokenValidMs;
            Date validity = new Date(now);
            return Jwts.builder()
                    .claim("userId", userId)
                    .claim("role", role)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(validity)
                    .signWith(secretKey)
                    .compact();

        }
        if (Objects.equals(type, "REFRESH")) {
            now += this.refreshTokenValidMs;
            Date validity = new Date(now);
            return Jwts.builder()
                    .claim("userId", userId)
                    .claim("role", role)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(validity)
                    .signWith(secretKey)
                    .compact();
        }
        return null;
    }


    public void addRefreshTokenInCookie(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
    }

    public void addAccessTokenInHeader(String accessToken, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + accessToken);
    }

}
