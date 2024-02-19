package com.example.payment_service.client;

import com.example.payment_service.domain.order.request.OrderCreateReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment",url = "localhost:8080")
public interface PaymentServiceClient {

//    @PostMapping ("/payments")// 결제 API 호경
//    void purchaseProduct(@RequestBody OrderCreateReq orderCreateReq);

    @GetMapping("/payments")// 결제 API 호경
    void purchaseProduct();

}
