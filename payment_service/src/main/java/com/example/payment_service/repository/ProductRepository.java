package com.example.payment_service.repository;

import com.example.payment_service.domain.ProductType;
import com.example.payment_service.domain.product.Product;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByProductType(ProductType type);

    Optional<Product> findProductByProductNumber(String productNumber);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    Optional<Product> findByProductNumber(String productNumber);


}
