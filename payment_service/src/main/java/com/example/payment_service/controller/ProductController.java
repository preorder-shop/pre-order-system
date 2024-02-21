package com.example.payment_service.controller;

import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.product.dto.ProductDto;
import com.example.payment_service.domain.stock.dto.StockDto;
import com.example.payment_service.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<List<ProductDto>> getProductList(){

        List<ProductDto> result = productService.getProductList();

        return ResponseEntity.ok().body(result);

    }

    /**
     * 예약 구매 상품 목록만 조회하는 API
     */
    @GetMapping("/pre-order")
    public ResponseEntity<List<ProductDto>> getPreOrderProducts(){

        List<ProductDto> result = productService.getPreOrderProducts();

        return ResponseEntity.ok().body(result);
    }

    /**
     * 일반 상품 목록만 조회하는 API
     */
    @GetMapping("/ordinary")
    public ResponseEntity<List<ProductDto>> getOrdinaryProducts(){

        List<ProductDto> result = productService.getOrdinaryProducts();

        return ResponseEntity.ok().body(result);
    }

    /**
     * 상품 상세 조회 API
     */
    @GetMapping("/{number}")
    public ResponseEntity<ProductDto> getProductInfo(@PathVariable("number") String productNumber){

        ProductDto result = productService.getProductInfo(productNumber);

        return ResponseEntity.ok().body(result);

    }

    /**
     * 상품 재고 수량 조회 API
     */
    @GetMapping ("/{number}/stock")
    public ResponseEntity<StockDto> getProductStock(@PathVariable("number") String productNumber){

        StockDto result = productService.getStock(productNumber);

        return ResponseEntity.ok().body(result);

    }

    /**
     * redis 에 상품 재고 수량 update
     */
    @GetMapping("/update-memory")
    public ResponseEntity<String> updateStockInMemory(){

        productService.updateStockInRedis();


        return ResponseEntity.ok().body("update complete");
    }

    /**
     * redis 에 있는 상품 재고 수량 db에 update
     */
    @GetMapping("/update-db")
    public ResponseEntity<String> updateStockInDB(){

        productService.updateStockInMemory();

        return ResponseEntity.ok().body("update complete");
    }









}