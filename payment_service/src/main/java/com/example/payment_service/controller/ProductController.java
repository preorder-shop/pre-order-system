package com.example.payment_service.controller;

import com.example.payment_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 목록을 조회하는 API
     */
    @GetMapping("")
    public void getProductList(){

    }







}
