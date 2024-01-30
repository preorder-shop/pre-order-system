package com.example.reservation.controller;

import static com.example.reservation.common.response.BaseResponseStatus.COMMENT_EMPTY;
import static com.example.reservation.common.response.BaseResponseStatus.USERS_EMPTY_USER_NAME;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.common.response.BaseResponse;
import com.example.reservation.dto.CreateCommentReq;
import com.example.reservation.dto.CreateCommentRes;
import com.example.reservation.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}")
    public BaseResponse<CreateCommentRes> createComment(@RequestHeader("Authorization") String auth, @RequestBody CreateCommentReq createCommentReq, @PathVariable("id") Long id){

        checkCommentValidation(createCommentReq.getContent());

        String jwtToken = auth.substring(7);
        CreateCommentRes createCommentRes = commentService.createComment(jwtToken, createCommentReq, id);

        return new BaseResponse<>(createCommentRes);
    }

    private void checkCommentValidation(String comment){
        if(comment==null || comment.isBlank())
            throw new BaseException(COMMENT_EMPTY);

    }

    /**
     * 좋아요 API
     */
    @GetMapping("/like/{id}")
    public BaseResponse<String> likeComment(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") Long id){

        String jwtToken = authorizationHeader.substring(7);
        String result = commentService.likeComment(jwtToken, id);

        return new BaseResponse<>(result);
    }
}
