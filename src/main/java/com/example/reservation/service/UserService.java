package com.example.reservation.service;

import com.example.reservation.dto.SignUpReq;
import com.example.reservation.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder encoder;

    public void signup(SignUpReq signUpReq){

        // todo : 예외처리 -> 비밀번호 중복확인

        String password = signUpReq.getPassword();
        String encodePassword = encoder.encode(password); // 비번 암호화
        User user = signUpReq.toEntity(encodePassword); // entity 생성



    }
}
