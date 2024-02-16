package com.example.payment_service.controller;

import com.example.payment_service.domain.product.response.ProductRes;
import com.example.payment_service.service.ProductService;
import java.util.List;
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
    public List<ProductRes> getProducts(){
        List<ProductRes> result = productService.getProducts();
        return result;
    }

    /**
     * 예약 구매 상품 번호를 조회하는 API
     */
    @GetMapping("/pre-order")
    public List<ProductRes> getPreOrderProducts(){
        List<ProductRes> result = productService.getPreOrderProducts();
        return result;
    }



}
