package com.example.activity_service.service;


import static com.example.activity_service.common.response.BaseResponseStatus.FOLLOW_INVALID;

import com.example.activity_service.client.UserServiceClient;
import com.example.activity_service.common.exceptions.BaseException;
import com.example.activity_service.common.jwt.JWTUtil;
import com.example.activity_service.domain.ActiveType;
import com.example.activity_service.dto.response.GetFollowerRes;
import com.example.activity_service.dto.response.UserDto;
import com.example.activity_service.entity.Follow;
import com.example.activity_service.entity.UserLog;
import com.example.activity_service.repository.FollowRepository;
import com.example.activity_service.repository.UserLogRepository;
import java.util.ArrayList;
import java.util.List;
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
    private final JWTUtil jwtUtil;
    private final UserLogRepository userLogRepository;
    private final UserServiceClient userServiceClient;


    public String followOther(String fromUserId, String toUserId) {

        String message;

        // Id 값으로 to 유저 확인

        userServiceClient.validateUserId(toUserId);

        if (Objects.equals(fromUserId, toUserId)) {
            throw new BaseException(FOLLOW_INVALID);
        }

        Optional<Follow> exist = followRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);

        if (exist.isPresent()) { // 이미 팔로우 했으면 취소 처리
            followRepository.delete(exist.get());

            UserLog userLog = UserLog.builder()
                    .actor(fromUserId)
                    .recipient(toUserId)
                    .activeType(ActiveType.CANCEL_FOLLOW)
                    .build();

            userLogRepository.save(userLog);

            message="팔로우를 취소했습니다.";

        } else {
            Follow follow = Follow.builder()
                    .toUserId(toUserId)
                    .fromUserId(fromUserId)
                    .build();

            followRepository.save(follow);

            UserLog userLog = UserLog.builder()
                    .actor(fromUserId)
                    .recipient(toUserId)
                    .activeType(ActiveType.FOLLOW)
                    .build();

            userLogRepository.save(userLog);

            message="해당 사용자를 팔로우했습니다.";

        }

        return message;
    }

    public List<GetFollowerRes> getFollowers(Long userId){

        List<Follow> follow = followRepository.findAllByToUserId(userId);

        List<GetFollowerRes> users = new ArrayList<>();

        follow.forEach(f-> users.add(new GetFollowerRes(f.getFromUserId())));

        return users;
    }
}
