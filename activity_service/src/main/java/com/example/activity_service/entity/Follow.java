package com.example.activity_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Follow extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "to_user",nullable = false)
//    private User toUser; //  follow 신청 받은 유저
//
//    @ManyToOne
//    @JoinColumn(name = "from_user",nullable = false)
//    private User fromUser; // follow 를 신청한 id

    @Column(nullable = false,name = "toUserId")
    private String toUserId;  // follow 신청 받은 유저 id

    @Column(nullable = false,name = "fromUserId")
    private String fromUserId; // follow 를 신청한 유저 id

    // from -> to from 이 to 로 follow 요청

//    @Builder
//    public Follow(User toUser,User fromUser){
//        this.toUser = toUser;
//        this.fromUser = fromUser;
//    }

    @Builder
    public Follow(String toUserId,String fromUserId){
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}

