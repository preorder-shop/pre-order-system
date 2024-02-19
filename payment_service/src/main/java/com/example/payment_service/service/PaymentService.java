package com.example.payment_service.service;

import com.example.payment_service.client.PaymentServiceClient;
import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.OrderState;
import com.example.payment_service.domain.order.dto.OrderDto;
import com.example.payment_service.domain.order.dto.PaymentDto;
import com.example.payment_service.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderRepository orderRepository;

    private final PaymentServiceClient paymentServiceClient;

    @Transactional
    public String createPayment(OrderDto orderDto, double prob) {

        log.info("결제 진입 prob :{}",prob);

        if (prob < 0.2) { // 요청중 20%는 취소 처리.
            return "fail-1";
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

        String result = "";

        try{
            result = paymentServiceClient.paymentForProduct(paymentDto);
        }catch (FeignException e){
            log.error(e.getMessage());
            result = "feign error";
            order.changeStateToCancel();
        }

        return result;

    }

    @Transactional
    public String paymentForOrder(PaymentDto paymentDto ,double prob) {

        Order order = orderRepository.findById(paymentDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("잘못된 주문 id"));

        log.info("결제 진행 prob: {}",prob);

        if (prob < 0.2) { // 요청중 20%는 취소 처리.
            order.changeStateToCancel();
            return "fail-2";
        }

        //todo: (redis)


        order.changeStateToComplete();

        return "success";

    }


}
