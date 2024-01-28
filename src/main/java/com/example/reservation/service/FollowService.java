package com.example.reservation.service;

import static com.example.reservation.common.response.BaseResponseStatus.*;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.entity.Follow;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.FollowRepository;
import com.example.reservation.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public void followOther(String token,Long id){
        // todo
        // 토큰값으로 from 유저 확인

        User fromUser = userRepository.findByEmailAndRole(jwtUtil.getEmail(token), jwtUtil.getRole(token))
                .orElseThrow(()->new BaseException(INVALID_TOKEN));

        // Id 값으로 to 유저 확인
        User toUser = userRepository.findById(id)
                .orElseThrow(()->new BaseException(USERS_INVALID_ID));

        Follow follow = Follow.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .build();

        followRepository.save(follow);

    }
}
