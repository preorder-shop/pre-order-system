//package com.example.activity_service.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import java.time.LocalDateTime;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
//@Getter
//@Entity
//public class Post {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String title;
//
//    private String content;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @CreationTimestamp
//    @Column(nullable = false,name="createdAt")
//    private LocalDateTime created_at;
//
//    @UpdateTimestamp
//    @Column(nullable = false,name = "updatedAt")
//    private LocalDateTime updated_at;
//
//    @Builder
//    public Post(String title,String content,User user){
//        this.title = title;
//        this.content = content;
//        this.user = user;
//    }
//
//
//
//}
