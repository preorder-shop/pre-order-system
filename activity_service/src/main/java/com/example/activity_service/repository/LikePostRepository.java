package com.example.activity_service.repository;

import com.example.activity_service.entity.LikePost;
import com.example.activity_service.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost,Long> {

    Optional<LikePost> findByUserIdAndPost(String userEmail, Post post);

    List<LikePost> findAllByUserId(String userId);
}
