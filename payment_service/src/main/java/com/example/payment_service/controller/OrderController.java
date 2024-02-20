package com.example.payment_service.controller;

import com.example.payment_service.domain.order.dto.OrderResponseDto;
import com.example.payment_service.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    /**
     * 주문정보 조회 API
     */
    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> getMyOrder(@RequestParam(name = "userId") String userId){

        List<OrderResponseDto> result = orderService.getMyOrderList(userId);

        return ResponseEntity.ok().body(result);
    }
}
