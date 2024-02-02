package com.example.user_service.repository;

import com.example.user_service.entity.Certification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification,Long> {

    Optional<Certification> findByEmail(String email);

    Optional<Certification> findByEmailAndCode(String email,String code);

    void deleteByEmail(String email);
}
