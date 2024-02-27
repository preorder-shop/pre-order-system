package com.example.product_service.repository;

import com.example.product_service.domain.ProductType;

import com.example.product_service.domain.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {


    List<Product> findALlByProductType(ProductType type);

    Optional<Product> findByProductNumber(String productNumber);






}
