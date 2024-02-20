package com.example.payment_service.controller;

import com.example.payment_service.domain.order.dto.PaymentDto;
import com.example.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/pay")
    public String payment(@RequestBody PaymentDto paymentDto){
        log.info("결제 api 호출");

        double prob = Math.random();

        String result = paymentService.paymentForOrder(paymentDto,prob);

        return result;

    }


}
