package com.example.reservation.repository;

import com.example.reservation.entity.User;
import com.example.reservation.entity.User.State;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRole(String email,String role);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByIdAndState(Long id, State state);

    Boolean existsByEmail(String email);

}
