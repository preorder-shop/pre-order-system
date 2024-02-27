package com.example.stockservice.controller;

import com.example.stockservice.dto.StockResponseDto;
import com.example.stockservice.repository.StockRedisRepository;
import com.example.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;
    private final StockRedisRepository stockRedisRepository;

    @GetMapping("/{productId}")
    public void getStock(@PathVariable(name = "productId") String productId){

      //  stockService.getStock(productId);

       // return ResponseEntity.ok();

    }

    @GetMapping("/increment/{productId}")
    public ResponseEntity<String> testIncrement(@PathVariable(name = "productId") String productId){
        Long result = stockRedisRepository.increaseOne(productId);

        return ResponseEntity.ok().body(result.toString());
    }

    @GetMapping("/decrement/{productId}")
    public ResponseEntity<String> testDecrement(@PathVariable(name = "productId") String productId){
      //  Long result = stockRedisRepository.decreaseOne(productId);

        Long result = stockRedisRepository.decreaseByCount(productId,3);

        return ResponseEntity.ok().body(result.toString());
    }
}
