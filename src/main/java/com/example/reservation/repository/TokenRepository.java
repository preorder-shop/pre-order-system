package com.example.reservation.repository;

import com.example.reservation.entity.Token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {

    boolean existsByEmail(String email);
    Optional<Token> findByRefreshTokenAndEmail(String token,String email);

    List<Token> findByEmail(String email);

    void deleteByEmail(String email);
}
