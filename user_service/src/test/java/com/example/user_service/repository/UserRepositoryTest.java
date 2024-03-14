package com.example.user_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.user_service.common.config.RedisConfig;
import com.example.user_service.common.config.S3Config;
import com.example.user_service.common.config.SecurityConfig;
import com.example.user_service.common.jwt.JWTUtil;
import com.example.user_service.entity.User;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Import({SecurityConfig.class, JWTUtil.class, RedisConfig.class})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @DisplayName("특정 이메일로 가입한 유저를 조회한다.")
    @Test
    void findByEmail(){
        //given

        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .name("test")
                .email("test@naver.com")
                .password(encoder.encode("1234"))
                .greeting("hello~!")
                .build();

        userRepository.save(user);

        //when
        User findUser = userRepository.findByEmail("test@naver.com").get();

        //then
        assertThat(findUser)
                .extracting("name","password","greeting")
                .containsExactly(
                        "test",encoder.encode("1234"),"hello~!"
                );

    }

}