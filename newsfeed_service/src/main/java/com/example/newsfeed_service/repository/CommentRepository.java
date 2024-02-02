package com.example.newsfeed_service.repository;

import com.example.newsfeed_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
