package com.example.payment_service.service;

import com.example.payment_service.client.PaymentServiceClient;
import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.dto.OrderDto;
import com.example.payment_service.domain.order.dto.PaymentDto;
import com.example.payment_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderRepository orderRepository;

    private final PaymentServiceClient paymentServiceClient;


    public String createPayment(OrderDto orderDto, double prob) {

        if (prob < 0.2) { // 요청중 20%는 취소 처리.
            return "fail";
        }

        Order order = Order.builder()
                .userId(orderDto.getUserId())
                .productNumber(orderDto.getOrderProductNumber())
                .quantity(1)
                .build();

        Order savedOrder = orderRepository.save(order);
        PaymentDto paymentDto = PaymentDto.builder().orderId(savedOrder.getId())
                .productNumber(savedOrder.getProductNumber())
                .userId(
                        savedOrder.getUserId()).build();

        String orderId = paymentServiceClient.paymentForProduct(paymentDto);
        return orderId;

    }

    public String paymentForOrder(PaymentDto paymentDto ,double prob) {

        Order order = orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("잘못된 주문 id"));


        if (prob < 0.2) { // 요청중 20%는 취소 처리.
            order.changeStateToCancel();
            return "fail";
        }

        //todo: 결제 진행.


        order.changeStateToComplete();

        return "success";

    }


}
