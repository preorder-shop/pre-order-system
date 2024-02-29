package com.example.user_service.repository;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class EmailRedisRepository {

    private final RedisTemplate<String, String> redisTemplate; // 이메일 : 인증번호

    public void saveEmailCertificationNumber(String email, String number){
        redisTemplate.opsForValue().set(email,number);
        redisTemplate.expire(email,600, TimeUnit.SECONDS);
    }

    public boolean checkEmailCertificationNumber(String email, String number){
        String getNumber = redisTemplate.opsForValue().get(email);
        if(getNumber==null)
            return false;
        if(!getNumber.equals(number))
            return false;
        return true;
    }

    public void deleteEmailCertificationNumber(String email){
        redisTemplate.delete(email);
    }

}
