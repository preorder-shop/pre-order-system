package com.example.reservation.entity;

import com.example.reservation.domain.ActiveType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "actor",nullable = false)
    private User actor; // 해당 활동을 한 유저

    @ManyToOne
    @JoinColumn(name = "recipient",nullable = false)
    private User recipient; // 특정 행위를 당한 유저

    @Column(nullable = false)
    private String log; //  활동 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActiveType activeType;

    @CreationTimestamp
    @Column(nullable = false,name = "createdAt")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updated_at;


    @Builder
    public UserLog(User actor, User recipient, String log,ActiveType activeType){
        this.actor = actor;
        this.recipient = recipient;
        this.log = log;
        this.activeType = activeType;
    }


}
