package com.example.reservation.repository;

import com.example.reservation.entity.Follow;
import com.example.reservation.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    List<Follow> findAllByFromUser(User user);
}
