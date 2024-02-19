package com.example.payment_service.domain.product.response;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRes {

    private String productNumber; // 상품 번호
    private ProductType productType;
    private String name;
    private int price;


    @Builder
    private ProductRes(String productNumber, ProductType productType, String name,
                       int price) {
        this.productNumber = productNumber;
        this.productType = productType;
        this.name = name;
        this.price = price;
    }

    public static ProductRes of(Product product) {
        return ProductRes.builder()
                .productNumber(product.getProductNumber())
                .productType(product.getProductType())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
