package com.example.reservation.repository;

import com.example.reservation.entity.Certification;
import jakarta.validation.constraints.Email;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification,Long> {

    Optional<Certification> findByEmail(String email);

    Optional<Certification> findByEmailAndCode(String email,String code);

    void deleteByEmail(String email);
}
