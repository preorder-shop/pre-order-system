package com.example.stockservice.service;

import com.example.stockservice.entity.Stock;
import com.example.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

//    @Cacheable(value = "",key = "#productId",cacheManager = "redisCahceManager")
//    public int getStock(String productId){
//
//
//    }

    private Stock findStockInDB(String productId){

        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("없는 상품 정보 요청"));
        return stock;
    }

    private void updateCache(String productId,int quantity){

    }

    private void findStockInCache(String productId){

    }
}
