package com.example.product_service.repository;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductRedisRepository {

    private final RedisTemplate redisTemplate;
    private final HashOperations<String,String,Object> hashOperations;


    public void saveProduct(String productId, Map<String,String> productInfo, long ttlInDays){
        hashOperations.putAll(productId,productInfo);
        redisTemplate.expire(productId,ttlInDays, TimeUnit.DAYS);
    }

    public Map<String,Object> getProduct(String productId){
        return hashOperations.entries(productId);
    }

}
