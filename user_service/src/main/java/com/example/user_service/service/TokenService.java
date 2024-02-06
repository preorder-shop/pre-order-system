package com.example.user_service.service;

import com.example.user_service.common.exceptions.BaseException;
import com.example.user_service.common.response.BaseResponseStatus;
import com.example.user_service.entity.Token;
import com.example.user_service.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public void validateRefreshToken(String refreshToken, String email) {

        tokenRepository.findByRefreshTokenAndEmail(refreshToken, email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.TOKEN_INVALID));

    }
}
