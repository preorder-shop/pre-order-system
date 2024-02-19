package com.example.payment_service.controller;

import com.example.payment_service.domain.order.dto.PaymentDto;
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


    @PostMapping("/pay")
    public void payment(@RequestBody PaymentDto paymentDto){


    }


}
