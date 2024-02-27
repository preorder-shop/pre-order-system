package com.example.payment_service.repository;

import com.example.payment_service.domain.stock.Stock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Optional<Stock> findByProductId(String productId);
}
