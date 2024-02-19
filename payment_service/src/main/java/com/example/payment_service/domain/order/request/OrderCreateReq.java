package com.example.payment_service.domain.order.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderCreateReq {


    private String userId; // 주문한 유저 id
    private String productNumber; // 주문 상품 번호
    private int quantity;  // 주문한 수량 (지금은 한개로 fix)
    @Builder
    private OrderCreateReq(String productNumber){
        this.productNumber = productNumber;
    }
}
