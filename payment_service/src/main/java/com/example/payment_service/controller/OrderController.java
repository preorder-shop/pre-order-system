package com.example.payment_service.controller;

import com.example.payment_service.domain.order.request.OrderCreateReq;
import com.example.payment_service.service.OrderService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    public static final LocalTime PRE_ORDER_OPEN_TIME = LocalTime.of(14, 0); // 오후 2시
    public static final LocalTime PRE_ORDER_CLOSE_TIME = LocalTime.of(19, 0); // 오후 7시
    private final OrderService orderService;

//    @PostMapping("/new") // 결제 화면 진입 (특정 시간이 되야 진입이 가능)
//    public void createOrder(@RequestBody OrderCreateReq request) {
//        System.out.println("결제 화면 진입 api 호출");
//        LocalTime registerTime = LocalDateTime.now().toLocalTime();
//
//        if(registerTime.isBefore(PRE_ORDER_OPEN_TIME)){
//            throw new IllegalArgumentException("주문 시간이 아닙니다.");
//        }
//
//        double probability = Math.random();
//        orderService.createOrder(request, probability);
//    }

    @GetMapping("/new") // 결제 화면 진입 (특정 시간이 되야 진입이 가능)
    public void createOrder() {
        System.out.println("결제 화면 진입 api 호출");
        LocalTime registerTime = LocalDateTime.now().toLocalTime();

        double probability = Math.random();
        orderService.createOrder(probability);
    }
    @GetMapping()
    public void initStockInCache() {
        // 재고 수량 초기화 -> 스케줄러로?
        orderService.initStockInCache();

    }

    public void deleteStockInCache() {

    }


}
