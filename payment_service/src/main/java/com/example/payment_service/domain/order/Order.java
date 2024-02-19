package com.example.payment_service.domain.order;

import com.example.payment_service.domain.OrderState;
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
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId; // 주문자

    @Column(name = "product_id")
    private String productId;

    @Column(name ="order_state" )
    private OrderState orderState;

    @Builder
    public Order(String userId, String productId,OrderState orderState){
        this.userId = userId;
        this.productId = productId;
        this.orderState = orderState;
    }

    public void changeOrderState(OrderState state){
        this.orderState = state;

    }




}
