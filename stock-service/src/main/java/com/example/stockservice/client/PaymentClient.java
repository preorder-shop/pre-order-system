package com.example.stockservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "payment")
public class PaymentClient {


}
