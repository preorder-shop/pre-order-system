package com.example.payment_service.service;


import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.product.dto.ProductDto;
import com.example.payment_service.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;


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


}
