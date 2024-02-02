package com.example.user_service.service;


import static com.example.user_service.common.response.BaseResponseStatus.FOLLOW_INVALID;
import static com.example.user_service.common.response.BaseResponseStatus.USERS_INVALID_EMAIL;
import static com.example.user_service.common.response.BaseResponseStatus.USERS_INVALID_ID;

import com.example.user_service.common.exceptions.BaseException;
import com.example.user_service.common.jwt.JWTUtil;
import com.example.user_service.domain.ActiveType;
import com.example.user_service.entity.Follow;
import com.example.user_service.entity.User;
import com.example.user_service.entity.User.State;
import com.example.user_service.entity.UserLog;
import com.example.user_service.repository.FeedRepository;
import com.example.user_service.repository.FollowRepository;
import com.example.user_service.repository.UserRepository;
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

    public String followOther(String userEmail, Long id) {

        String log;
        String message="";
        ActiveType activeType = null;

        // 토큰값으로 from 유저 확인
        User fromUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BaseException(USERS_INVALID_EMAIL));

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
            message = "팔로우를 취소했습니다.";

            activeType = ActiveType.CANCEL_FOLLOW;

        } else {
            Follow follow = Follow.builder()
                    .toUser(toUser)
                    .fromUser(fromUser)
                    .build();

            followRepository.save(follow);

            log = fromUser.getName() + "님이 " + toUser.getName() + " 님을 팔로우 했습니다.";

            message = "해당 사용자를 팔로우했습니다.";

            activeType=ActiveType.FOLLOW;

        }

        UserLog userLog = UserLog.builder()
                .actor(fromUser)
                .recipient(fromUser)
                .log(log)
                .activeType(activeType)
                .build();
        feedRepository.save(userLog);

        return message;
    }
}
