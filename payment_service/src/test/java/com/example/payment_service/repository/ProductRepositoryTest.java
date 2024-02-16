package com.example.payment_service.repository;

import static com.example.payment_service.domain.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

import com.example.payment_service.domain.product.Product;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void before(){
        Product product1 = Product.builder()
                .productNumber("001")
                .productType(PRE_ORDER)
                .name("아이패드")
                .price(1500)
                .stock(10)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .productType(NORMAL)
                .name("양말")
                .price(300)
                .stock(30)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .productType(PRE_ORDER)
                .name("맥북")
                .price(5000)
                .stock(3)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }



    @DisplayName("예약 구매 상품 목록만 조회한다.")
    @Test
    void findAllByProductType(){
        List<Product> products = productRepository.findAllByProductType(PRE_ORDER);

        assertThat(products).hasSize(2)
                .extracting("productNumber","name","productType")
                .containsExactlyInAnyOrder(
                        tuple("001","아이패드",PRE_ORDER),
                        tuple("003","맥북",PRE_ORDER)
                );

    }

}