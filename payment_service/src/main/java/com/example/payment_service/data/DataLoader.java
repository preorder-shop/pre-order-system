package com.example.payment_service.data;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.stock.Stock;
import com.example.payment_service.repository.ProductRepository;
import com.example.payment_service.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final ProductRepository productRepository;

    private final StockRepository stockRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Product productOne = Product.builder()
                .productNumber("001")
                .name("mac-book")
                .productType(ProductType.PRE_ORDER)
                .price(50000000)
                .build();


        Product productTwo = Product.builder()
                .productNumber("002")
                .name("pencil")
                .productType(ProductType.NORMAL)
                .price(2000)
                .build();

        Product productThree = Product.builder()
                .productNumber("003")
                .name("iphone")
                .productType(ProductType.PRE_ORDER)
                .price(25900)
                .build();
        Stock stockOne = Stock.builder()
                .productId("001")
                .quantity(10)
                .build();


        Stock stockTwo = Stock.builder()
                .productId("002")
                .quantity(25)
                .build();

        Stock stockThree = Stock.builder()
                .productId("003")
                .quantity(30)
                .build();

        stockRepository.save(stockOne);
        stockRepository.save(stockTwo);
        stockRepository.save(stockThree);

        productRepository.save(productOne);
        productRepository.save(productTwo);
        productRepository.save(productThree);


    }
}
