package com.example.payment_service.client;

import com.example.payment_service.domain.order.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment" ,url = "localhost:8084")
public interface PaymentServiceClient {

    @PostMapping("/pay")
    String paymentForProduct(@RequestBody PaymentDto paymentDto);
}
