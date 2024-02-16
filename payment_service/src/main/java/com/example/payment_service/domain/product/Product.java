package com.example.payment_service.domain.product;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Entity(name = "product")
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id; // pk

    @Column(name = "product_number",nullable = false)
    private String productNumber; // 상품 번호

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;


    @Builder
    private Product(String productNumber ,ProductType productType ,String name, int price, int stock){
            this.productNumber = productNumber;
            this.productType = productType;
            this.name = name;
            this.price = price;
            this.stock = stock;
    }

    public void decrease(int stock){
        if(this.stock-stock<0){
            throw new RuntimeException("재고는 0개 미만이 될 수 없습니다.");
        }
        this.stock-=stock;
    }
}
