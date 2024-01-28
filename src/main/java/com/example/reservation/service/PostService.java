package com.example.reservation.service;


import static com.example.reservation.common.response.BaseResponseStatus.INVALID_TOKEN;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.common.response.BaseResponse;
import com.example.reservation.dto.CreatePostReq;
import com.example.reservation.dto.CreatePostRes;
import com.example.reservation.entity.Post;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.PostRepository;
import com.example.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final JWTUtil jwtUtil;

    public CreatePostRes createPost(String jwt,CreatePostReq createPostReq){

        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(()->new BaseException(INVALID_TOKEN));

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
}
