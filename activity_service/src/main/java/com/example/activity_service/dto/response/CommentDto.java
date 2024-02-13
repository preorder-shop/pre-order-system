package com.example.activity_service.dto.response;

import com.example.activity_service.entity.Comment;
import com.example.activity_service.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {

    private Long commentId;
    private String writer; // 글 쓴 사람
    private String content;

    @Builder
    private CommentDto(Long commentId, String writer, String content){
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;

    }

    public static CommentDto of(Comment comment){
        return CommentDto.builder()
                .commentId(comment.getId())
                .writer(comment.getUserId())
                .content(comment.getContent())
                .build();
    }


}
