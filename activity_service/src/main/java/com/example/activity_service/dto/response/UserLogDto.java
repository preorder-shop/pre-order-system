package com.example.activity_service.dto.response;


import com.example.activity_service.entity.UserLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLogDto {

    private String actor; // 행위자
    private String recipient;
    private String activity; // 활동 내용.

    @Builder
    private UserLogDto(String actor, String recipient, String activity){
        this.actor = actor;
        this.recipient = recipient;
        this.activity = activity;
    }

    public static UserLogDto of(UserLog userLog){
        return UserLogDto.builder()
                .actor(userLog.getActor())
                .recipient(userLog.getRecipient())
                .activity(userLog.getActiveType().toString())
                .build();

    }
}
