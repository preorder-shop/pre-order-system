package com.example.payment_service.controller;


import com.example.payment_service.config.RedisConfig;
import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.order.dto.OrderDto;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.stock.Stock;
import com.example.payment_service.repository.ProductRepository;
import com.example.payment_service.repository.ProductStockRepository;
import com.example.payment_service.repository.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = RedisConfig.class)
@SpringBootTest
class PaymentControllerTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private PaymentInitController paymentInitController;


    @BeforeEach
    void before() {
        Product productOne = Product.builder()
                .productNumber("001")
                .name("mac-book")
                .productType(ProductType.PRE_ORDER)
                .price(50000000)
                .build();

        Stock stockOne = Stock.builder()
                .productNumber("001")
                .quantity(100)
                .build();

        productRepository.save(productOne);
        stockRepository.save(stockOne);

    }

    @DisplayName("상품 재고 수량에 대한 동시성 테스트를 위해 1000개의 요청을받아 예약 구매 상품을 구매한다.")
    @Test
    void buyPreOrderProduct(){
        OrderDto orderDto =  OrderDto.builder().userId("testA").orderProductNumber("001").quantity(1).build();
        paymentInitController.paymentInit(orderDto);

        int stock = productStockRepository.getStock("001");
        Assertions.assertThat(stock).isEqualTo(99);


    }

}