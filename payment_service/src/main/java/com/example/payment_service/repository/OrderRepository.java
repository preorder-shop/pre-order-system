package com.example.payment_service.repository;

import com.example.payment_service.domain.order.Order;
import java.util.List;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByUserId(String userId);
}
