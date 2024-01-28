package com.example.reservation.service;

import static com.example.reservation.common.response.BaseResponseStatus.POST_ID_INVALID;
import static com.example.reservation.common.response.BaseResponseStatus.TOKEN_INVALID;

import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.dto.CreateCommentReq;
import com.example.reservation.dto.CreateCommentRes;
import com.example.reservation.dto.CreatePostReq;
import com.example.reservation.entity.Comment;
import com.example.reservation.entity.Post;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.repository.CommentRepository;
import com.example.reservation.repository.PostRepository;
import com.example.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final JWTUtil jwtUtil;

    public CreateCommentRes createComment(String jwt, CreateCommentReq createCommentReq, Long postId){

        String email = jwtUtil.getEmail(jwt);
        String role = jwtUtil.getRole(jwt);

        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(()->new BaseException(TOKEN_INVALID));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_ID_INVALID));

        Comment build = Comment.builder()
                .content(createCommentReq.getContent())
                .user(user)
                .post(post)
                .build();

        Comment comment = commentRepository.save(build);

        return CreateCommentRes.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .writer(comment.getUser().getName())
                .postId(comment.getPost().getId())
                .build();

    }

}
