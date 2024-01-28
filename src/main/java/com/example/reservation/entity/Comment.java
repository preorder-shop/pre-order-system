package com.example.reservation.entity;

import com.example.reservation.entity.User.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
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
@Getter
@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @CreationTimestamp
    @Column(nullable = false,name="createdAt")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false,name = "updatedAt")
    private LocalDateTime updated_at;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private User.State state;



    public enum State {
        ACTIVE,
        BLACK, // 신고로 차단 / 블랙 글
        DELETE // 삭제한 글
    }

    @Builder
    public Comment(String content, User user,Post post){
        this.content = content;
        this.user = user;
        this.post = post;
        this.state = User.State.ACTIVE;
    }


}
