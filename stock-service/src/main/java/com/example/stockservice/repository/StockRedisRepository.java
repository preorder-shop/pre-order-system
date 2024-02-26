package com.example.stockservice.repository;

import com.example.stockservice.entity.ProductType;
import com.example.stockservice.entity.Stock;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StockRedisRepository {

    private final StockRepository stockRepository;

    private final RedisTemplate<String, String> redisTemplate;

    public void initStockInformation() { // 재고 db에 있는 예약 구매 상품 수량을 redis 에 올림
        List<Stock> stocks = stockRepository.findAllByProductType(ProductType.PRE_ORDER);
        for (Stock stock : stocks) {
            redisTemplate.opsForValue().set(stock.getProductId(), String.valueOf(stock.getQuantity()));
        }
    }

    public Long decreaseOne(String productId) {

        Long decrement = redisTemplate.opsForValue().decrement(productId);

        if (decrement == 0) {
            Stock stock = stockRepository.findByProductId(productId)
                    .orElseThrow(() -> new IllegalStateException("유효하지 않은 상품 번호"));
            stock.updateStock(0);

        }

        if (decrement < 0) {
            throw new IllegalArgumentException("재고는 0이 될수 없습니다.");
        }

        return decrement;


    }

    public Long increaseOne(String productId) {

        Long stock = redisTemplate.opsForValue().increment(productId);
        return stock;

    }

    public Long decreaseByCount(String productId, int count) {

        Long decrement = redisTemplate.opsForValue().decrement(productId, count);

        return decrement;
    }

    public Long increaseByCount(String productId, int count) {
        Long decrement = redisTemplate.opsForValue().decrement(productId, count);

        return decrement;
    }


}
