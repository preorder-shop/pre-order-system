package com.example.product_service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "payment" ,url = "localhost:8084/payments")
public interface PaymentServiceClient {


}
