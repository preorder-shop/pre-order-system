package com.example.activity_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class LikePost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "userId")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "postId")
    private Post post;

    @CreationTimestamp
    @Column(nullable = false,name = "createdAt")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updated_at;

    @Builder
    public LikePost(String userId,Post post){
        this.userId = userId;
        this.post = post;
    }

}
