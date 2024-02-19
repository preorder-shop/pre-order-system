package com.example.payment_service.domain.stock;

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
@Table(name = "stock")
@Getter
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_number", nullable = false)
    private String productNumber;


    @Column(nullable = false)
    private int stock;


    public void decreaseStock(int minus) { // 재고 수량 감소
        if (this.stock - minus < 0) {
            throw new RuntimeException("재고는 0개 미만이 될 수 없습니다.");
        }
        this.stock -= minus;

    }

    public void increaseStock(int count) {
        this.stock += count;
    }

    @Builder
    public Stock(String productNumber, int stock){
        this.productNumber = productNumber;
        this.stock = stock;

    }


}
