package com.example.payment_service.repository;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByProductType(ProductType type);

}
