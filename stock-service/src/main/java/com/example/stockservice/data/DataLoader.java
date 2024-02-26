package com.example.stockservice.data;

import com.example.stockservice.entity.ProductType;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final StockRepository stockRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Stock stockOne = Stock.builder()
                .productId("001")
                .quantity(10)
                .productType(ProductType.PRE_ORDER)
                .build();


        Stock stockTwo = Stock.builder()
                .productId("002")
                .quantity(25)
                .productType(ProductType.PRE_ORDER)
                .build();

        Stock stockThree = Stock.builder()
                .productId("003")
                .quantity(30)
                .productType(ProductType.NORMAL)
                .build();

        stockRepository.save(stockOne);
        stockRepository.save(stockTwo);
        stockRepository.save(stockThree);

    }
}
