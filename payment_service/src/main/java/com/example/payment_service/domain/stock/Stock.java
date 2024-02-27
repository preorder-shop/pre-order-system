package com.example.payment_service.domain.stock;


import com.example.payment_service.domain.BaseEntity;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "stock")
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(nullable = false, name = "product_id")
    private String productId;


    @Column(nullable = false)
    private int quantity;


    public void decreaseStock(int minus) { // 재고 수량 감소
        if (this.quantity - minus < 0) {
            throw new RuntimeException("재고는 0개 미만이 될 수 없습니다.");
        }
        this.quantity -= minus;

    }

    public void increaseStock(int plus) {
        this.quantity += plus;
    }

    public void changeStock(int stock){
        this.quantity = stock;
    }


    @Builder
    public Stock(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;

    }


}
