package com.example.reservation.repository;

import com.example.reservation.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRole(String email,String role);

    Optional<User> findByEmailAndPassword(String email, String password);

    Boolean existsByEmail(String email);
}
