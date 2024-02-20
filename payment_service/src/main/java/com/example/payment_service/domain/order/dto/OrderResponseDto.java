package com.example.payment_service.domain.order.dto;

import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.OrderState;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Long id;
    private String userId;
    private String productNumber;
    private OrderState orderState;
    private LocalDateTime orderDate;

    @Builder
    private OrderResponseDto(Long id, String userId, String productNumber, OrderState orderState,LocalDateTime time){
        this.id = id;
        this.userId = userId;
        this.productNumber = productNumber;
        this.orderState = orderState;
        this.orderDate = time;
    }

    static public OrderResponseDto of(Order order){
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productNumber(order.getProductNumber())
                .orderState(order.getOrderState())
                .time(order.getUpdatedDateTime())
                .build();

    }
}
