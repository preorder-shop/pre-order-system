package com.example.activity_service.service;


import static com.example.activity_service.common.response.BaseResponseStatus.POST_ID_INVALID;
import static com.example.activity_service.common.response.BaseResponseStatus.USERS_INVALID_EMAIL;

import com.example.activity_service.client.UserServiceClient;
import com.example.activity_service.common.exceptions.BaseException;
import com.example.activity_service.common.jwt.JWTUtil;
import com.example.activity_service.domain.ActiveType;
import com.example.activity_service.dto.request.CreatePostReq;
import com.example.activity_service.dto.response.CreatePostRes;
import com.example.activity_service.entity.LikePost;
import com.example.activity_service.entity.Post;
import com.example.activity_service.entity.UserLog;
import com.example.activity_service.repository.LikePostRepository;
import com.example.activity_service.repository.PostRepository;
import com.example.activity_service.repository.UserLogRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserServiceClient userServiceClient;

    private final JWTUtil jwtUtil;
    private final LikePostRepository likePostRepository;
    private final UserLogRepository userLogRepository;

    public CreatePostRes createPost(String userEmail, CreatePostReq createPostReq) {

        Post buildPost = Post.builder()
                .title(createPostReq.getTitle())
                .content(createPostReq.getContent())
                .userId(userEmail)
                .build();

        Post post = postRepository.save(buildPost);

 //       String log = user.getName() + "님이 " + post.getTitle() + " 이라는 제목의 글을 작성했습니다.";

        UserLog userLog = UserLog.builder()
                .actor(userEmail)
                .recipient(post.getUserId())
                .activeType(ActiveType.WRITE_POST)
                .build();

        userLogRepository.save(userLog);

        return CreatePostRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUserId())
                .build();

    }

    public String likePost(String userEmail, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_ID_INVALID));

        //  자기 자신의 글은 좋아요 불가
//        if(Objects.equals(user.getId(), post.getUser().getId())){
//            return "자기 자신의 글은 좋아요를 할수 없습니다.";
//        }


        Optional<LikePost> byUserAndPost = likePostRepository.findByUserIdAndPost(userEmail, post);

        if (!byUserAndPost.isPresent()) {

            LikePost likePost = LikePost.builder()
                    .userId(userEmail)
                    .post(post)
                    .build();

            likePostRepository.save(likePost);

            UserLog userLog = UserLog.builder()
                    .actor(userEmail)
                    .recipient(post.getUserId())
                    .activeType(ActiveType.LIKE_POST)
                    .build();

            userLogRepository.save(userLog);

            return "해당 글에 좋아요를 완료했습니다.";

        }
        // todo : 나중에 state 상태 변경으로 바꾸기.
        likePostRepository.delete(byUserAndPost.get());


        UserLog userLog = UserLog.builder()
                .actor(userEmail)
                .recipient(post.getUserId())
                .activeType(ActiveType.CANCEL_LIKE_POST)
                .build();

        userLogRepository.save(userLog);

        return "해당 글에 좋아요를 취소했습니다.";

    }
}
