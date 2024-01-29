package com.example.reservation.service;


import static com.example.reservation.common.response.BaseResponseStatus.POST_ID_INVALID;
import static com.example.reservation.common.response.BaseResponseStatus.TOKEN_INVALID;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.dto.CreatePostReq;
import com.example.reservation.dto.CreatePostRes;
import com.example.reservation.entity.LikePost;
import com.example.reservation.entity.Post;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.LikePostRepository;
import com.example.reservation.repository.PostRepository;
import com.example.reservation.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final LikePostRepository likePostRepository;

    public CreatePostRes createPost(String jwt,CreatePostReq createPostReq){

        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(()->new BaseException(TOKEN_INVALID));

        Post buildPost = Post.builder()
                .title(createPostReq.getTitle())
                .content(createPostReq.getContent())
                .user(user)
                .build();

        Post post = postRepository.save(buildPost);

        return CreatePostRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getUser().getName())
                .build();

    }

    public String likePost(String jwt,Long postId){

        // 해당 유저로 해당 글에 좋아요를 한 적이 있으면 좋아요를 취소시키고
        // 종아요를 한 적이 없으면 좋아요 추가
        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(()->new BaseException(TOKEN_INVALID));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_ID_INVALID));

        //  자기 자신의 글은 좋아요 불가
//        if(Objects.equals(user.getId(), post.getUser().getId())){
//            return "자기 자신의 글은 좋아요를 할수 없습니다.";
//        }

        Optional<LikePost> byUserAndPost = likePostRepository.findByUserAndPost(user, post);

        if(!byUserAndPost.isPresent()){
            // 객체 생성
            LikePost likePost = LikePost.builder()
                    .user(user)
                    .post(post)
                    .build();

            likePostRepository.save(likePost);
            return "좋아요를 완료했습니다.";

        }
        // todo : 나중에 state 상태 변경으로 바꾸기.
        likePostRepository.delete(byUserAndPost.get());
        return "좋아요를 취소했습니다.";

    }
}
