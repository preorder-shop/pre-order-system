package com.example.reservation.service;

import static com.example.reservation.response.BaseResponseStatus.USERS_INVALID_EMAIL;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.dto.GetFeedRes;
import com.example.reservation.entity.Feed;
import com.example.reservation.entity.Follow;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.FeedRepository;
import com.example.reservation.repository.FollowRepository;
import com.example.reservation.repository.UserRepository;
import java.util.ArrayList;
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

    private final FollowRepository followRepository;

    public List<GetFeedRes> getFeedList(String userEmail){

        List<GetFeedRes> result = new ArrayList<>();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BaseException(USERS_INVALID_EMAIL));


        List<Follow> follows = followRepository.findAllByFromUser(user);
        List<User> users = follows.stream().map(Follow::getToUser).toList(); // 내가 팔로우한 유저 목록

        if(users.size()>0){
            List<Feed> allByUserIn = feedRepository.findAllByUserIn(users);
            for (Feed feed : allByUserIn) {
                result.add(new GetFeedRes(feed.getLog()));
            }
            // todo : paging 처리 + 정렬 순서


        }

        return result;
    }
}
