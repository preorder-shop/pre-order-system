package com.example.reservation.repository;

import com.example.reservation.entity.Comment;
import com.example.reservation.entity.LikeComment;
import com.example.reservation.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment,Long> {

    Optional<LikeComment> findByUserAndComment(User user, Comment comment);
}
