package com.example.user_service;

import com.example.user_service.common.CertificationNumber;
import com.example.user_service.repository.EmailRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailCertificationTests {

    @Autowired
    private EmailRedisRepository emailRedisRepository;

    private String userEmail;

    @AfterEach
    void afterTest(){
        emailRedisRepository.deleteEmailCertificationNumber(userEmail);
    }

    @DisplayName("이메일 인증을 할때 redis 에 이메일과 인증 코드가 제대로 들어가는지 확인한다.")
    @Test
    void testEmailCertificationInformationSaveInRedis(){
        //given
        userEmail="user1@gmail.com";
        String certificationNumber = CertificationNumber.getCertificationNumber();

        //when
        emailRedisRepository.saveEmailCertificationNumber(userEmail,certificationNumber);

        //then
        boolean result = emailRedisRepository.checkEmailCertificationNumber(userEmail, certificationNumber);
        Assertions.assertThat(result).isEqualTo(true);

    }
}
