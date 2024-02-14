package com.example.activity_service.controller;

import static com.example.activity_service.common.response.BaseResponseStatus.COMMENT_EMPTY;

import com.example.activity_service.common.exceptions.BaseException;
import com.example.activity_service.dto.request.CreateCommentReq;
import com.example.activity_service.dto.response.CommentDto;
import com.example.activity_service.dto.response.CreateCommentRes;
import com.example.activity_service.common.response.BaseResponse;
import com.example.activity_service.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/activity/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성 API
     */
    @PostMapping("/{id}")
    public BaseResponse<CreateCommentRes> createComment(@RequestHeader("Authorization") String auth, @RequestBody CreateCommentReq createCommentReq, @PathVariable("id") Long id){

        checkCommentValidation(createCommentReq.getContent());

        String jwtToken = auth.substring(7);
        CreateCommentRes createCommentRes = commentService.createComment(jwtToken, createCommentReq, id);

        return new BaseResponse<>(createCommentRes);
    }

    /**
     * 내가 작성한 댓글 조회 API
     * @return
     */

    @GetMapping ("")
    public BaseResponse<List<CommentDto>> getMyCommentList(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        List<CommentDto> result = commentService.getMyCommentList(userId);
        return new BaseResponse<>(result);

    }



    /**
     * 댓글 좋아요 API
     */
    @GetMapping("/like/{id}")
    public BaseResponse<String> likeComment(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") Long id){

        String jwtToken = authorizationHeader.substring(7);
        String result = commentService.likeComment(jwtToken, id);

        return new BaseResponse<>(result);
    }

    /**
     * 내가 좋아요한 댓글 조회 API
     */
    @GetMapping("/like")
    public BaseResponse<List<CommentDto>> getMyLikeCommentList(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        List<CommentDto> result = commentService.getMyLikeCommentList(userId);
        return new BaseResponse<>(result);
    }

    private void checkCommentValidation(String comment){
        if(comment==null || comment.isBlank())
            throw new BaseException(COMMENT_EMPTY);

    }

}
