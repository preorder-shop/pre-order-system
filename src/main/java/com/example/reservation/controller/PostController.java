package com.example.reservation.controller;

import static com.example.reservation.response.BaseResponseStatus.POST_EMPTY_CONTENT;
import static com.example.reservation.response.BaseResponseStatus.POST_EMPTY_TITLE;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.response.BaseResponse;
import com.example.reservation.dto.request.CreatePostReq;
import com.example.reservation.dto.response.CreatePostRes;
import com.example.reservation.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public BaseResponse<CreatePostRes> createPost(@RequestBody CreatePostReq createPostReq){

        checkTitleValidation(createPostReq.getTitle());
        checkContentValidation(createPostReq.getContent());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        CreatePostRes createPostRes = postService.createPost(userEmail, createPostReq);

        return new BaseResponse<>(createPostRes);
    }

    /**
     * 좋아요 API
     */
    @GetMapping ("/like/{id}")
    public BaseResponse<String> likePost(@PathVariable("id") Long id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        String result = postService.likePost(userEmail, id);

        return new BaseResponse<>(result);
    }

    private void checkTitleValidation(String title){
        if(title==null || title.isBlank()){
            throw new BaseException(POST_EMPTY_TITLE);
        }

    }

    private void checkContentValidation(String content){
        if(content==null || content.isBlank()){
            throw new BaseException(POST_EMPTY_CONTENT);
        }
    }
}
