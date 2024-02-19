package com.example.payment_service.controller;

import com.example.payment_service.domain.order.request.OrderCreateReq;
import com.example.payment_service.domain.payment.PaymentDto;
import com.example.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 처리 API
//    @PostMapping("") // 실제 결제를 시도하는 API
//    public void purchaseProduct(@RequestBody OrderCreateReq orderCreateReq){
//        double probability = Math.random();
//        paymentService.purchase(probability,orderCreateReq);
//    }

    @GetMapping("") // 실제 결제를 시도하는 API
    public void purchaseProduct(){
        double probability = Math.random();
        paymentService.purchase(probability);
    }


}
