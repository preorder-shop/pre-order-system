package com.example.user_service.service;

import com.example.user_service.entity.Token;
import com.example.user_service.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token validateRefreshToken(String refreshToken,String email){

        Token token = tokenRepository.findByRefreshTokenAndEmail(refreshToken, email)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리프레시 토큰값입니다."));
        return token;
    }
}
