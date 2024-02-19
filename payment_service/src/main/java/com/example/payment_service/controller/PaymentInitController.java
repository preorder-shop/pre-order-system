package com.example.payment_service.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentInitController {

    public static final LocalTime PRE_ORDER_OPEN_TIME = LocalTime.of(14, 0); // 오후 2시
    public static final LocalTime PRE_ORDER_CLOSE_TIME = LocalTime.of(19, 0); // 오후 7시

    /**
     * 결제 진입 API
     */
    @PostMapping ("")
    public ResponseEntity<String> paymentInit(){

        LocalTime registerTime = LocalDateTime.now().toLocalTime();

        if(registerTime.isBefore(PRE_ORDER_OPEN_TIME)|| registerTime.isAfter(PRE_ORDER_CLOSE_TIME)){
            return ResponseEntity.status(403).body("지금은 주문 시간이 아닙니다.");
        }

        double prob = Math.random();

        return ResponseEntity.ok().body("해당 예약 상품을 주문했습니다.");

    }

}
