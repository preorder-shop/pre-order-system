package com.example.activity_service.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.activity_service.entity.Follow;
import com.netflix.discovery.converters.Auto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class FollowRepositoryTest {

    @Autowired
    private  FollowRepository followRepository;


    @DisplayName("내가 팔로우한 유저 목록을 확인할 수 있다.")
    @Test
    void test(){
        String userId="userA";
        Follow followOne = Follow.builder().fromUserId(userId).toUserId("userB").build();
        Follow followTwo = Follow.builder().fromUserId(userId).toUserId("userC").build();
        Follow followThree = Follow.builder().fromUserId(userId).toUserId("userD").build();

        followRepository.save(followOne);
        followRepository.save(followTwo);
        followRepository.save(followThree);

        // when
        List<Follow> allByFromUserId = followRepository.findAllByFromUserId(userId);

        // then
        Assertions.assertThat(allByFromUserId).hasSize(3);



    }

}