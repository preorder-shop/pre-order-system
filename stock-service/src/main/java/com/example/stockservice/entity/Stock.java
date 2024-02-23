package com.example.stockservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "stock")
@Entity
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(nullable = false ,name = "product_id" , updatable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Builder
    public Stock(String productId, int quantity){
        this.productId = productId;
        this.quantity = quantity;
    }


    public void increaseStock(int num){
        this.quantity+=num;
    }

    public void decreaseStock(int num){
        this.quantity-=num;
    }

    public void updateStock(int num){
        this.quantity = num;
    }



}
