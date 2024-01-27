package com.example.reservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Entity
@Getter
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser; //  follow 신청 받은 유저

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser; // follow 를 신청한 id

    // from -> to from 이 to 로 follow 요청
}

