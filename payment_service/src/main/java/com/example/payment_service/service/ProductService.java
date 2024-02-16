package com.example.payment_service.service;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import com.example.payment_service.domain.product.response.ProductRes;
import com.example.payment_service.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductRes> getProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductRes::of).collect(Collectors.toList());
    }

    public List<ProductRes> getPreOrderProducts(){
        List<Product> products = productRepository.findAllByProductType(ProductType.PRE_ORDER);
        return products.stream().map(ProductRes::of).collect(Collectors.toList());
    }
}
