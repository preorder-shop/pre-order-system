package com.example.payment_service.scheduler;

import com.example.payment_service.repository.StockRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableScheduling
public class DataScheduler {

    private final StockRedisRepository stockRedisRepository;

    @Scheduled(cron = "0 0 13 * * *") // 초 분 시 일 월 요일
    public void updatePreOrderProductStockInRedis(){
        log.info("스케줄러 실행");
        stockRedisRepository.initStockInfo();
    }

    @Scheduled(cron = "0 1 19 * * *")
    public void updatePreOrderProductStockInDB(){
        stockRedisRepository.updateStockInDB();
    }

}
