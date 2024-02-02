package com.example.newsfeed_service.repository;

import com.example.newsfeed_service.entity.LikePost;
import com.example.newsfeed_service.entity.Post;
import com.example.newsfeed_service.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost,Long> {

    Optional<LikePost> findByUserAndPost(User user, Post post);
}
