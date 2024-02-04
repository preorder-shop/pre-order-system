package com.example.activity_service.service;

import com.example.activity_service.entity.Token;
import com.example.activity_service.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public void validateRefreshToken(String refreshToken, String email){

     tokenRepository.findByRefreshTokenAndEmail(refreshToken, email)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리프레시 토큰값입니다."));
    }
}
