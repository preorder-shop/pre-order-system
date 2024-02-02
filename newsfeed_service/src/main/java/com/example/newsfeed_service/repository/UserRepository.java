package com.example.newsfeed_service.repository;

import com.example.newsfeed_service.entity.User;
import com.example.newsfeed_service.entity.User.State;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRole(String email,String role);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByIdAndState(Long id, State state);

    Boolean existsByEmail(String email);

}
