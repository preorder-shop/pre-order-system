package com.example.payment_service.config;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.stock.Stock;
import com.example.payment_service.repository.ProductRepository;
import com.example.payment_service.repository.StockRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public DataLoader(ProductRepository productRepository,StockRepository stockRepository){
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Product product1 = Product.builder().productNumber("001")
                .name("mac-book")
                .productType(ProductType.PRE_ORDER)
                .price(50000)
                .build();

//        Product product2 = Product.builder().productNumber("002")
//                .name("shoes")
//                .productType(ProductType.PRE_ORDER)
//                .price(2000)
//                .build();

        Product product3 = Product.builder().productNumber("003")
                .name("book")
                .productType(ProductType.NORMAL)
                .price(3200)
                .build();

        Stock stock1 = Stock.builder().productNumber("001").stock(10).build();

        Stock stock2 = Stock.builder().productNumber("003").stock(300).build();


        productRepository.save(product1);
     //   productRepository.save(product2);
        productRepository.save(product3);

        stockRepository.save(stock1);
        stockRepository.save(stock2);

    }
}
