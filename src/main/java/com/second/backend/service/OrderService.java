package com.second.backend.service;

import com.second.backend.model.Orders;
import com.second.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // 사용자 ID로부터 모든 주문을 가져오는 메소드
    public List<Orders> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }
}
