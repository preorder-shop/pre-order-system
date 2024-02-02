package com.example.user_service.repository;

import com.example.user_service.entity.Comment;
import com.example.user_service.entity.LikeComment;
import com.example.user_service.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment,Long> {

    Optional<LikeComment> findByUserAndComment(User user, Comment comment);
}
