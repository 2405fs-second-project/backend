// OrderService.java
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

    public Orders createOrder(Orders order) {
        return orderRepository.save(order);
    }

    public Orders getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
