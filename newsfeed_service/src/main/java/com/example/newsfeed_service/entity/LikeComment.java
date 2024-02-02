package com.example.newsfeed_service.entity;

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
public class LikeComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false,name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false,name = "comment_id")
    private Comment comment;

    @CreationTimestamp
    @Column(nullable = false,name = "createdAt")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updated_at;

    @Builder
    public LikeComment(User user,Comment comment){
        this.user = user;
        this.comment = comment;
    }



}
