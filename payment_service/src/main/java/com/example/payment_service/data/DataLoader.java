package com.example.payment_service.data;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.repository.ProductRepository;
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

        productRepository.save(productOne);
        productRepository.save(productTwo);

    }
}
