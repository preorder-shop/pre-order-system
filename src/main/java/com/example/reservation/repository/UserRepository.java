package com.example.reservation.repository;

import com.example.reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    Boolean existsByEmail(String email);
}
