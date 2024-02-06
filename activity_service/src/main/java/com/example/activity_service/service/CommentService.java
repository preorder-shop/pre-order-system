package com.example.activity_service.service;

import static com.example.activity_service.common.response.BaseResponseStatus.COMMENT_ID_INVALID;
import static com.example.activity_service.common.response.BaseResponseStatus.POST_ID_INVALID;
import static com.example.activity_service.common.response.BaseResponseStatus.TOKEN_INVALID;

import com.example.activity_service.common.exceptions.BaseException;
import com.example.activity_service.common.jwt.JWTUtil;
import com.example.activity_service.domain.ActiveType;
import com.example.activity_service.dto.request.CreateCommentReq;
import com.example.activity_service.dto.response.CreateCommentRes;
import com.example.activity_service.entity.Comment;
import com.example.activity_service.entity.LikeComment;
import com.example.activity_service.entity.Post;
import com.example.activity_service.entity.UserLog;
import com.example.activity_service.repository.CommentRepository;
import com.example.activity_service.repository.LikeCommentRepository;
import com.example.activity_service.repository.PostRepository;
import com.example.activity_service.repository.UserLogRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final JWTUtil jwtUtil;
    private final LikeCommentRepository likeCommentRepository;
    private final UserLogRepository userLogRepository;

    public CreateCommentRes createComment(String jwt, CreateCommentReq createCommentReq, Long postId) {


        // jwt 토큰에서 유저를 식별하는 값을 가져옴. -> 이걸 Comment table 에 댓글을 작성한 유저 식별값으로 넣음.
        String email = jwtUtil.getEmail(jwt);

        // 댓글을 작성할 포스트의 아이디가 제대로 된건지 검증.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_ID_INVALID));

        Comment build = Comment.builder()
                .content(createCommentReq.getContent()) // 댓글 내용
                .userId(email)  // 댓글 작성자
                .post(post)
                .build();

        Comment comment = commentRepository.save(build);

    //    String log = user.getName()+"님이 "+post.getUser().getName()+"의 글에 "+comment.getContent()+" 라는 내용의 댓글을 작성했습니다." ;

        UserLog userLog = UserLog.builder()
                .actor(email)
                .recipient(post.getUserId())
                .activeType(ActiveType.WRITE_COMMENT)
                .build();

        userLogRepository.save(userLog);

        return CreateCommentRes.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .writer(comment.getUserId())
                .postId(comment.getPost().getId())
                .build();

    }

    public String likeComment(String jwt, Long commentId) {

        String userEmail = jwtUtil.getEmail(jwt);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(COMMENT_ID_INVALID));


        Optional<LikeComment> byUserAndComment = likeCommentRepository.findByUserIdAndComment(userEmail, comment);

        if (!byUserAndComment.isPresent()) {

            LikeComment likeComment = LikeComment.builder()
                    .userId(userEmail)
                    .comment(comment)
                    .build();

            likeCommentRepository.save(likeComment);


            UserLog userLog = UserLog.builder()
                    .actor(userEmail)
                    .recipient(comment.getUserId())
                    .activeType(ActiveType.LIKE_COMMENT)
                    .build();

            userLogRepository.save(userLog);

            return "해당 댓글에 좋아요를 완료했습니다.";

        }

        // todo : 나중에 state 상태 변경으로 바꾸기.
        likeCommentRepository.delete(byUserAndComment.get());


        UserLog userLog = UserLog.builder()
                .actor(userEmail)
                .recipient(comment.getUserId())
                .activeType(ActiveType.CANCEL_LIKE_COMMENT)
                .build();

        userLogRepository.save(userLog);

        return "해당 댓글에 좋아요를 취소했습니다.";

    }


}
