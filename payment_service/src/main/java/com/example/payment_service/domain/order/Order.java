package com.example.payment_service.domain.order;


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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "user_id")
    private String userId;

    @Column(nullable = false, name = "product_number")
    private String productNumber;

    @Column(nullable = false)
    private int quantity; // 주문 수량 (지금은 한개로 fix)

    @Builder
    public Order(String userId, String productNumber, int quantity){
        this.userId = userId;
        this.productNumber = productNumber;
        this.quantity = quantity;
    }
}
