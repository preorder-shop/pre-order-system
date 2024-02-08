package com.example.activity_service.repository;

import com.example.activity_service.entity.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {

   Optional<Follow> findByFromUserIdAndToUserId(String fromUserId, String toUserId);
//
//    List<Follow> findAllByFromUser(User user);

    List<Follow> findAllByToUserId(Long userId); // 나를 팔로우한 유저 목록
}
