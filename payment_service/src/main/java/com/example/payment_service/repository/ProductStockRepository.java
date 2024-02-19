package com.example.payment_service.repository;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.stock.Stock;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProductStockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private final ProductRepository productRepository;

    private final StockRepository stockRepository;

    public void saveStock(String productNumber, int stockQuantity) {
        redisTemplate.opsForValue().set(productNumber, String.valueOf(stockQuantity));
    }

    public int getStock(String productNumber) {
        String stockQuantity = redisTemplate.opsForValue().get(productNumber);
        return stockQuantity != null ? Integer.parseInt(stockQuantity) : 0;
    }

    public int increaseStock(String productNumber) {
        int stock = getStock(productNumber);
        stock = stock + 1;
        saveStock(productNumber, stock);
        return stock;
    }

    public int decreaseStock(String producNumber) {
        int stock = getStock(producNumber);
        stock = stock - 1;
        if (stock < 0) {
            throw new IllegalArgumentException("해당 상품은 재고 소진으로 더이상 구매가 불가능합니다.");
        }
        saveStock(producNumber, stock);
        return stock;
    }

    public void deleteKey(String productNumber) {
        Boolean delete = redisTemplate.delete(productNumber);
        log.info("deleteKey ={}", productNumber);
        log.info("success ={}", delete);

    }

    public void initStockInfo() {
        List<Product> preProducts = productRepository.findAllByProductType(ProductType.PRE_ORDER);// 예약 상품목록만 가져옴.
        for (Product preProduct : preProducts) {
            String productNumber = preProduct.getProductNumber();
            Stock stock = stockRepository.findByProductNumber(productNumber)
                    .orElseThrow(() -> new IllegalArgumentException("상품 번호 불일치"));
            saveStock(preProduct.getProductNumber(), stock.getStock());
        }
    }

    public void buyerIncrease() {
        String stockQuantity = redisTemplate.opsForValue().get("buyer");
        int count = Integer.parseInt(stockQuantity);
        count += 1;
       redisTemplate.opsForValue().set("buyer",String.valueOf(count));
    }


}
