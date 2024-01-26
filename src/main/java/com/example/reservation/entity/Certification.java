package com.example.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Certification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String certificationNumber;

    @CreationTimestamp
    @Column(nullable = false,name = "createdAt")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false,name = "updatedAt")
    private LocalDateTime updated_at; // -> 나중에 칼럼 삭제의 기준


    @Builder
    public Certification(String email, String certificationNumber){
        this.email = email;
        this.certificationNumber = certificationNumber;
        this.created_at = LocalDateTime.now();
    }

    public void changeCertificationNumber(String number){
        this.certificationNumber = number;
    }


}
