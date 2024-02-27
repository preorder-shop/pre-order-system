package com.example.product_service.data;

import com.example.product_service.domain.ProductType;
import com.example.product_service.domain.entity.Product;
import com.example.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final ProductRepository productRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Product productOne = Product.builder()
                .productNumber("123")
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



        productRepository.save(productOne);
        productRepository.save(productTwo);
        productRepository.save(productThree);


    }
}
