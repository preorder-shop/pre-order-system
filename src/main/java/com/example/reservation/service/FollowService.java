package com.example.reservation.service;

import static com.example.reservation.common.response.BaseResponseStatus.*;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.entity.Feed;
import com.example.reservation.entity.Follow;
import com.example.reservation.entity.User;
import com.example.reservation.entity.User.State;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.FeedRepository;
import com.example.reservation.repository.FollowRepository;
import com.example.reservation.repository.UserRepository;
import java.util.Objects;
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
    private final FeedRepository feedRepository;

    public void followOther(String token, Long id) {

        String log;

        // 토큰값으로 from 유저 확인
        User fromUser = userRepository.findByEmailAndRole(jwtUtil.getEmail(token), jwtUtil.getRole(token))
                .orElseThrow(() -> new BaseException(TOKEN_INVALID));

        // Id 값으로 to 유저 확인
        User toUser = userRepository.findByIdAndState(id, State.ACTIVE)
                .orElseThrow(() -> new BaseException(USERS_INVALID_ID));

        if (Objects.equals(fromUser.getId(), toUser.getId())) {
            throw new BaseException(FOLLOW_INVALID);
        }

        Optional<Follow> exist = followRepository.findByFromUserAndToUser(fromUser, toUser);

        if (exist.isPresent()) { // 이미 팔로우 했으면 취소 처리
            followRepository.delete(exist.get());
            log = fromUser.getName() + "님이 " + toUser.getName() + " 님을 팔로우 취소 했습니다.";

        } else {
            Follow follow = Follow.builder()
                    .toUser(toUser)
                    .fromUser(fromUser)
                    .build();

            followRepository.save(follow);

            log = fromUser.getName() + "님이 " + toUser.getName() + " 님을 팔로우 했습니다.";

        }

        Feed feed = Feed.builder()
                .user(fromUser)
                .name(fromUser.getName())
                .log(log)
                .build();
        feedRepository.save(feed);

    }
}
