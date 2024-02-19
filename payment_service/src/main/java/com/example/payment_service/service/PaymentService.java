package com.example.payment_service.service;

import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {


    public String createPayment(OrderDto orderDto, double prob) {

        if (prob < 0.2) { // 요청중 20%는 취소 처리.
            return "fail";
        }

        // todo : 결제 api 호출
        Order order = Order.builder()
                .userId(orderDto.getUserId())
                .productNumber(orderDto.getOrderProductNumber())
                .quantity(1)
                .build();





    }


}
