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
import com.example.activity_service.entity.User;
import com.example.activity_service.entity.UserLog;
import com.example.activity_service.repository.CommentRepository;
import com.example.activity_service.repository.FeedRepository;
import com.example.activity_service.repository.LikeCommentRepository;
import com.example.activity_service.repository.PostRepository;
import com.example.activity_service.repository.UserRepository;
import java.util.Optional;
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
    private final LikeCommentRepository likeCommentRepository;
    private final FeedRepository feedRepository;

    public CreateCommentRes createComment(String jwt, CreateCommentReq createCommentReq, Long postId) {

        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(() -> new BaseException(TOKEN_INVALID));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(POST_ID_INVALID));

        Comment build = Comment.builder()
                .content(createCommentReq.getContent())
                .user(user)
                .post(post)
                .build();

        Comment comment = commentRepository.save(build);

        String log = user.getName()+"님이 "+post.getUser().getName()+"의 글에 "+comment.getContent()+" 라는 내용의 댓글을 작성했습니다." ;

        UserLog userLog = UserLog.builder()
                .actor(user)
                .recipient(user)
                .log(log)
                .activeType(ActiveType.WRITE_COMMENT)
                .build();
        feedRepository.save(userLog);

        return CreateCommentRes.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .writer(comment.getUser().getName())
                .postId(comment.getPost().getId())
                .build();

    }

    public String likeComment(String jwt, Long commentId) {

        User user = userRepository.findByEmailAndRole(jwtUtil.getEmail(jwt), jwtUtil.getRole(jwt))
                .orElseThrow(() -> new BaseException(TOKEN_INVALID));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(COMMENT_ID_INVALID));

        String userName = user.getName();
        String commentUserName = comment.getUser().getName();

        Optional<LikeComment> byUserAndComment = likeCommentRepository.findByUserAndComment(user, comment);

        if (!byUserAndComment.isPresent()) {
            // 객체 생성
            LikeComment likeComment = LikeComment.builder()
                    .user(user)
                    .comment(comment)
                    .build();

            likeCommentRepository.save(likeComment);

            String log = userName+"님이 "+commentUserName+"님의 "+comment.getContent() +" 내용의 댓글을 좋아합니다." ;

            UserLog userLog = UserLog.builder()
                    .actor(user)
                    .recipient(comment.getUser())
                    .log(log)
                    .activeType(ActiveType.LIKE_COMMENT)
                    .build();
            feedRepository.save(userLog);

            return "해당 댓글에 좋아요를 완료했습니다.";

        }
        // todo : 나중에 state 상태 변경으로 바꾸기.
        likeCommentRepository.delete(byUserAndComment.get());

        String log = userName+"님이 "+commentUserName+"님의 "+comment.getContent() +" 내용의 댓글에 좋아요를 취소했습니다." ;

        UserLog userLog = UserLog.builder()
                .actor(user)
                .recipient(comment.getUser())
                .log(log)
                .activeType(ActiveType.CANCEL_LIKE_COMMENT)
                .build();
        feedRepository.save(userLog);

        return "해당 댓글에 좋아요를 취소했습니다.";

    }


}
