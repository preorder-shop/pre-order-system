package com.example.payment_service.domain.order.dto;

import com.example.payment_service.domain.order.OrderState;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Long id;
    private String userId;
    private String productNumber;
    private OrderState orderState;
    private LocalDateTime orderDate;
}
