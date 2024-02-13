package com.example.activity_service.service;

import static com.example.activity_service.common.response.BaseResponseStatus.UNEXPECTED_ERROR;

import com.example.activity_service.common.exceptions.BaseException;
import com.example.activity_service.dto.request.GetNewsFeedReq;
import com.example.activity_service.dto.response.NewsFeedDto;
import com.example.activity_service.entity.Follow;
import com.example.activity_service.entity.Post;
import com.example.activity_service.repository.FollowRepository;
import com.example.activity_service.repository.JdbcRepository;
import com.example.activity_service.repository.PostRepository;
import com.example.activity_service.repository.UserLogRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NewsFeedService {

    private final UserLogRepository userLogRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    private final JdbcRepository jdbcRepository;

    public List<NewsFeedDto> getPostListByCondition(GetNewsFeedReq getNewsFeedReq){

        String userId = getNewsFeedReq.getUserId();
        String type = getNewsFeedReq.getType(); // all, follow
        String sort = getNewsFeedReq.getSort(); // date, like
        int startPage = getNewsFeedReq.getStartPage(); // 시작 페이지

        if(sort.equals("date") && type.equals("all")){
            PageRequest pageRequest = PageRequest.of(startPage,5, Sort.by(Sort.Direction.DESC,"id"));
            Slice<Post> postSlice = postRepository.findAllBy(pageRequest); // 가장 최근에 작성한 순서대로 5개씩 잘라서 return
            List<Post> content = postSlice.getContent();
            List<NewsFeedDto> collect = content.stream().map(NewsFeedDto::of).collect(Collectors.toList());
            return collect;
        }

        if(sort.equals("date") && type.equals("follow")){
            PageRequest pageRequest = PageRequest.of(startPage,5, Sort.by(Sort.Direction.DESC,"id"));
            List<Follow> myFollowList = followRepository.findAllByFromUserId(userId); // 내가 팔로우한 유저 목록
            List<String> myFollowIdList = myFollowList.stream().map(Follow::getToUserId).toList();
            Slice<Post> postSlice = postRepository.findAllByUserIdIn(myFollowIdList,pageRequest); // 가장 최근에 작성한 순서대로 5개씩 잘라서 return
            List<Post> content = postSlice.getContent();

            List<NewsFeedDto> collect = content.stream().map(NewsFeedDto::of).collect(Collectors.toList());

            return collect;
        }

        List<NewsFeedDto> result=null;
        if(sort.equals("like") && type.equals("all")){

           result = jdbcRepository.getPostListByCondition(userId, "all");

            if(result==null){
                throw new BaseException(UNEXPECTED_ERROR);
            }

        }

        if(sort.equals("like") && type.equals("follow")){
           result = jdbcRepository.getPostListByCondition(userId, "follow");

            if(result==null){
                throw new BaseException(UNEXPECTED_ERROR);
            }

        }

        return result;


    }
}
