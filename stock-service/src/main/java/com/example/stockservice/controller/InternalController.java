package com.example.stockservice.controller;

import com.example.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internals")
public class InternalController {

    private final StockService stockService;

    @GetMapping("/init-redis")
    public void updateRedis(){

        stockService.updateRedis();

    }

}
