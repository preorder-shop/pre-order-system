package com.example.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String greeting;

    @Column(name = "profileImgUrl")
    private String profile_img_url;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updated_at;

    @Column(nullable = false)
    private String role; // 사용자 권한 관련

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state = State.ACTIVE;



    @Builder
    public User(String name, String email, String password,String role,String greeting) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.greeting = greeting;

    }

    public void changeRoleToAdmin() {
        this.role = "ROLE_ADMIN";
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeGreeting(String greeting){
        this.greeting = greeting;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public enum State {
        ACTIVE,
        INACTIVE, // 휴면계정 (오랫동안 접속 안했을때)
        BLACK, // 신고로 차단 / 블랙 계정
        DELETE // 탈퇴한 유저
    }

}
