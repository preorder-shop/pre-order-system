package com.example.activity_service.entity;

import com.example.activity_service.domain.State;
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

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false)
    private String userId; // userId로 이메일값 사용 (추후 변경 가능)

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

//    @Column(nullable = false,name = "postId")
//    private Long postId;

    @CreationTimestamp
    @Column(nullable = false,name="createdAt")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false,name = "updatedAt")
    private LocalDateTime updated_at;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;


    @Builder
    public Comment(String content, String userId,Post post){
        this.content = content;
        this.userId = userId;
        this.post = post;
        this.state = State.ACTIVE;
    }


}
