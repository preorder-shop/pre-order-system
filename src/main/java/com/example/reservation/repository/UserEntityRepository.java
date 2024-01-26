package com.example.reservation.repository;

import com.example.reservation.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {


    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
