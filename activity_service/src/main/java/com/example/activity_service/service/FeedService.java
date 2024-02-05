//package com.example.activity_service.service;
//
//import static com.example.activity_service.common.response.BaseResponseStatus.USERS_INVALID_EMAIL;
//
//import com.example.activity_service.common.exceptions.BaseException;
//import com.example.activity_service.common.jwt.JWTUtil;
//import com.example.activity_service.dto.response.GetFeedRes;
//import com.example.activity_service.entity.Follow;
//import com.example.activity_service.entity.UserLog;
//import com.example.activity_service.repository.FollowRepository;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Transactional
//@RequiredArgsConstructor
//@Service
//public class FeedService {
//
//    private final FeedRepository feedRepository;
//    private final UserRepository userRepository;
//    private final JWTUtil jwtUtil;
//
//    private final FollowRepository followRepository;
//
//    public List<GetFeedRes> getFeedList(String userEmail){
//
//        List<GetFeedRes> result = new ArrayList<>();
//
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new BaseException(USERS_INVALID_EMAIL));
//
//
//        List<Follow> follows = followRepository.findAllByFromUser(user);
//        List<User> users = follows.stream().map(Follow::getToUser).toList(); // 내가 팔로우한 유저 목록
//
//        if(users.size()>0){
//            List<UserLog> allByUserIn = feedRepository.findAllByActorIn(users);
//            for (UserLog userLog : allByUserIn) {
//                result.add(new GetFeedRes(userLog.getLog()));
//            }
//            // todo : paging 처리 + 정렬 순서
//
//
//        }
//
//        return result;
//    }
//}
