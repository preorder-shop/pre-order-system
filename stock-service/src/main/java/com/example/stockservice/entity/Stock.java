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
    private int stock;

    @Builder
    public Stock(String productId, int stock){
        this.productId = productId;
        this.stock = stock;
    }


    public void increaseStock(int num){
        this.stock+=num;
    }

    public void decreaseStock(int num){
        this.stock-=num;
    }

    public void updateStock(int num){
        this.stock = num;
    }



}
