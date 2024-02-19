package com.example.payment_service.domain.payment;

import lombok.Getter;

@Getter
public class PaymentDto {

    private String userId; // 주문한 유저 id
    private String productNumber; // 주문 상품 번호
    private int quantity;  // 주문한 수량 (지금은 한개로 fix)
}
