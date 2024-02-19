package com.example.payment_service.service;


import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.product.dto.ProductDto;
import com.example.payment_service.domain.stock.Stock;
import com.example.payment_service.domain.stock.dto.StockDto;
import com.example.payment_service.repository.ProductRepository;
import com.example.payment_service.repository.StockRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;


    public List<ProductDto> getProductList(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> ProductDto.builder()
                .productNumber(product.getProductNumber())
                .name(product.getName())
                .productType(product.getProductType().toString())
                .price(product.getPrice())
                .build()
        ).collect(Collectors.toList());
    }

    public List<ProductDto> getPreOrderProducts(){

        List<Product> products = productRepository.findALlByProductType(ProductType.PRE_ORDER);

        return products.stream().map(product -> ProductDto.builder()
                .productNumber(product.getProductNumber())
                .name(product.getName())
                .productType(product.getProductType().toString())
                .price(product.getPrice())
                .build()
        ).collect(Collectors.toList());
    }


    public List<ProductDto> getOrdinaryProducts(){

        List<Product> products = productRepository.findALlByProductType(ProductType.NORMAL);

        return products.stream().map(product -> ProductDto.builder()
                .productNumber(product.getProductNumber())
                .name(product.getName())
                .productType(product.getProductType().toString())
                .price(product.getPrice())
                .build()
        ).collect(Collectors.toList());
    }

    public ProductDto getProductInfo(String productNumber) {

        Product product = productRepository.findByProductNumber(productNumber)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 번호 입니다."));

        return ProductDto.builder()
                .productNumber(product.getProductNumber())
                .name(product.getName())
                .productType(product.getProductType().toString())
                .price(product.getPrice())
                .build();

    }

    public StockDto getStock(String productNumber){

        Stock stock = stockRepository.findByProductNumber(productNumber)
                .orElseThrow(() -> new IllegalArgumentException("상품 번호 불일치"));

        return StockDto.builder()
                .productNumber(stock.getProductNumber())
                .quantity(stock.getQuantity())
                .build();


    }


}
