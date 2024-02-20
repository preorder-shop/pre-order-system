package com.example.payment_service.service;

import com.example.payment_service.client.PaymentServiceClient;
import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.OrderState;
import com.example.payment_service.domain.order.dto.OrderDto;
import com.example.payment_service.domain.order.dto.PaymentDto;
import com.example.payment_service.domain.stock.Stock;
import com.example.payment_service.repository.OrderRepository;
import com.example.payment_service.repository.ProductStockRepository;
import com.example.payment_service.repository.StockRepository;
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
    private final StockRepository stockRepository;

    private final ProductStockRepository productStockRepository;

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

        Long orderId = paymentDto.getOrderId();
        Order order = orderRepository.findById(orderId)
                        .orElseThrow(()->new IllegalArgumentException("잘못된 주문 id입니다."));

        log.info("결제 진행 prob: {}",prob);

        if (prob < 0.2) { // 요청중 20%는 취소 처리.
            order.changeStateToCancel();
            return "fail-2";
        }

        boolean result = productStockRepository.decreaseStock(paymentDto.getProductNumber());// redis 수량 -1
        if(!result){
            order.changeStateToCancel();
            return "fail-3";
        }

        order.changeStateToComplete();

        return orderId.toString();

    }

    public boolean noStockInDB(String productNumber){
        Stock stock = stockRepository.findByProductNumber(productNumber)
                .orElseThrow(() -> new IllegalStateException("유효한 상품번호가 아닙니다."));

        int quantity = stock.getQuantity();
        return quantity <= 0;
    }


}
