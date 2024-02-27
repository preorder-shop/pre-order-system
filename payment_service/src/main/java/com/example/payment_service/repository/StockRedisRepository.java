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
public class StockRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public void saveStock(String productNumber, int quantity) {
        redisTemplate.opsForValue().set(productNumber, String.valueOf(quantity));
    }

    public int getStock(String productNumber){
        String quantity = redisTemplate.opsForValue().get(productNumber);
        return quantity!=null?Integer.parseInt(quantity):0;
    }

    public boolean increaseStock(String productNumber){
        int stock = getStock(productNumber);
        stock = stock + 1;
        saveStock(productNumber, stock);
        return true;
    }

    public synchronized boolean decreaseStock(String productNumber) {
        int count = getStock(productNumber);
        if(count==0){

            Stock stock = stockRepository.findByProductId(productNumber)
                    .orElseThrow(() -> new IllegalStateException("유효하지 않은 상품 번호"));
            stock.changeStock(0);
            return false;
        }

        count = count - 1;
        saveStock(productNumber, count);
        return true;
    }

    public void initStockInfo() {
        List<Product> preProducts = productRepository.findALlByProductType(ProductType.PRE_ORDER);// 예약 상품목록만 가져옴.
        for (Product preProduct : preProducts) {
            String productNumber = preProduct.getProductNumber();
            Stock stock = stockRepository.findByProductId(productNumber)
                    .orElseThrow(() -> new IllegalArgumentException("상품 번호 불일치"));

            saveStock(preProduct.getProductNumber(), stock.getQuantity());
        }
    }

    public void updateStockInDB(){
        List<Product> preProducts = productRepository.findALlByProductType(ProductType.PRE_ORDER);// 예약 상품목록만 가져옴.
        for (Product preProduct : preProducts) {
            String productNumber = preProduct.getProductNumber();
            Stock stock = stockRepository.findByProductId(productNumber)
                    .orElseThrow(() -> new IllegalArgumentException("상품 번호 불일치"));

            int quantity = getStock(productNumber);
            stock.changeStock(quantity);

        }
    }
}
