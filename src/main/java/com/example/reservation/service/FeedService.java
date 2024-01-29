package com.example.reservation.service;

import static com.example.reservation.common.response.BaseResponseStatus.TOKEN_INVALID;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.dto.GetFeedRes;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.FeedRepository;
import com.example.reservation.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public List<GetFeedRes> getFeedList(String jwt){
        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(()->new BaseException(TOKEN_INVALID));

        // 내가 팔로우한 유저들의 id 값을 가져와서 그 값들에 포함되는 모든 feed 내용을 가져와서 시간순으로 정렬





    }
}
