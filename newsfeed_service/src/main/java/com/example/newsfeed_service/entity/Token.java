package com.example.newsfeed_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@Getter
@Entity
public class Token {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    private String email; // 유저 식별값

    private Date expiredDate;

    @CreationTimestamp
    @Column(nullable = false,name = "createdAt")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updated_at;

    @Builder
    public Token(String refreshToken,String email,Date expiredDate){
        this.refreshToken = refreshToken;
        this.email = email;
        this.expiredDate = expiredDate;
    }


}
