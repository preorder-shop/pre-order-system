package com.example.stockservice.repository;

import com.example.stockservice.entity.ProductType;
import com.example.stockservice.entity.Stock;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {

    Optional<Stock> findByProductId(String productId);

    List<Stock> findAllByProductType(ProductType productType);
}
