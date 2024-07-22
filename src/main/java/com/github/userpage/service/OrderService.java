package com.github.userpage.service;

import com.github.userpage.model.Orders;
import com.github.userpage.repository.OrderRepository;
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
