package com.example.user_service.repository;

import com.example.user_service.entity.Follow;
import com.example.user_service.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    List<Follow> findAllByFromUser(User user);
}
