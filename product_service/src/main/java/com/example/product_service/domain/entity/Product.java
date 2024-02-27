package com.example.product_service.domain.entity;

import com.example.product_service.domain.BaseEntity;
import com.example.product_service.domain.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false,name = "product_number")
    private String productNumber;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "product_type")
    private ProductType productType;

    @Column(nullable = false)
    private int price;


    @Builder
    private Product(String productNumber, String name, ProductType productType, int price){
        this.productNumber = productNumber;
        this.name = name;
        this.productType = productType;
        this.price = price;
    }




}
