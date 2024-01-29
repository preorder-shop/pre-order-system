package com.example.reservation.repository;

import com.example.reservation.entity.LikePost;
import com.example.reservation.entity.Post;
import com.example.reservation.entity.User;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost,Long> {

    Optional<LikePost> findByUserAndPost(User user, Post post);
}
