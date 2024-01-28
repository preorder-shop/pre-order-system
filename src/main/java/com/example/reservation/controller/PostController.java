package com.example.reservation.controller;

import com.example.reservation.common.response.BaseResponse;
import com.example.reservation.dto.CreatePostReq;
import com.example.reservation.dto.CreatePostRes;
import com.example.reservation.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public BaseResponse<CreatePostRes> createPost(@RequestHeader("Authorization") String authorizationHeader,@RequestBody CreatePostReq createPostReq){

        // todo : 형식적 validation

        String jwtToken = authorizationHeader.substring(7);
        CreatePostRes createPostRes = postService.createPost(jwtToken, createPostReq);

        return new BaseResponse<>(createPostRes);
    }
}
