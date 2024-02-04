package com.example.activity_service.repository;

import com.example.activity_service.entity.Comment;
import com.example.activity_service.entity.LikeComment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment,Long> {

    Optional<LikeComment> findByUserAndComment(User user, Comment comment);
}
