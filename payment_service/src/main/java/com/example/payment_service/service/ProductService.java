package com.example.payment_service.service;


import com.example.payment_service.domain.stock.Stock;
import com.example.payment_service.domain.stock.dto.StockDto;
import com.example.payment_service.repository.StockRedisRepository;
import com.example.payment_service.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final StockRepository stockRepository;

    private final StockRedisRepository stockRedisRepository;


    public StockDto getStock(String productNumber){

        Stock stock = stockRepository.findByProductId(productNumber)
                .orElseThrow(() -> new IllegalArgumentException("상품 번호 불일치"));

        return StockDto.builder()
                .productNumber(stock.getProductId())
                .quantity(stock.getQuantity())
                .build();


    }

    public void updatePreOrderProductStockInRedis(){

        stockRedisRepository.initStockInfo();

    }

    public void updatePreOrderProductStockInDB(){

        stockRedisRepository.updateStockInDB();

    }


}
