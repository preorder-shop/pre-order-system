package com.example.reservation.service;

import static com.example.reservation.common.response.BaseResponseStatus.TOKEN_INVALID;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.dto.GetFeedRes;
import com.example.reservation.entity.Feed;
import com.example.reservation.entity.Follow;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.FeedRepository;
import com.example.reservation.repository.FollowRepository;
import com.example.reservation.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

    public List<GetFeedRes> getFeedList(String jwt,int startPage){

        List<GetFeedRes> result = null;

        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(()->new BaseException(TOKEN_INVALID));

        // 내가 팔로우한 유저들의 id 값을 가져와서 그 값들에 포함되는 모든 feed 내용을 가져와서 시간순으로 정렬
        List<Follow> follows = followRepository.findAllByFromUser(user);
        List<User> users = follows.stream().map(Follow::getToUser).toList(); // 내가 팔로우한 유저 목록

        if(users.size()>0){
            // paging
            PageRequest pageRequest = PageRequest.of(startPage,7, Sort.by(Direction.DESC,"created_at"));
            Slice<Feed> feedSlice = feedRepository.findAllByUserIn(users, pageRequest); // 내가 팔로우한 유저의 로그 정보들 가져옴.
            List<String> logs = feedSlice.stream().map(Feed::getLog).toList();

            for (String log : logs) {
                result.add(new GetFeedRes(log));
            }

        }

        return result;
    }
}
