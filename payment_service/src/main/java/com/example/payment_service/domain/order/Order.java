package com.example.payment_service.domain.order;


import com.example.payment_service.domain.BaseEntity;
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
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false,name = "user_id")
    private String userId;

    @Column(nullable = false, name = "product_number")
    private String productNumber;

    @Column(nullable = false)
    private int quantity; // 주문 수량 (지금은 한개로 fix)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "order_state")
    private OrderState orderState;

    @Builder
    public Order(String userId, String productNumber, int quantity){
        this.userId = userId;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.orderState = OrderState.DOING;
    }

    public void changeStateToCancel(){
        this.orderState = OrderState.CANCEL;
    }

    public void changeStateToComplete(){
        this.orderState = OrderState.COMPLETE;
    }
}
