package com.example.activity_service.repository;

import com.example.activity_service.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByUserId(String userId);

}
