package com.example.payment_service.service;

import com.example.payment_service.domain.order.Order;
import com.example.payment_service.domain.order.dto.OrderResponseDto;
import com.example.payment_service.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;



    public List<OrderResponseDto> getMyOrderList(String userId){
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderResponseDto> orderResponseDtos = orders.stream().map(OrderResponseDto::of).collect(Collectors.toList());

        return orderResponseDtos;

    }
}
