package com.example.reservation.service;

import com.example.reservation.entity.Token;
import com.example.reservation.repository.TokenRepository;
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
