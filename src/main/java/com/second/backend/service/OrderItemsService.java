package com.second.backend.service;

import com.second.backend.dto.ViewOrderItemsResponse;
import com.second.backend.model.OrderItems;
import com.second.backend.repository.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemsService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public List<ViewOrderItemsResponse> getOrderItemsByUserId(Integer userId) {
        List<OrderItems> orderItems = orderItemsRepository.findByOrderUserId(userId);
        return orderItems.stream().map(ViewOrderItemsResponse::new).collect(Collectors.toList());
    }

    public List<ViewOrderItemsResponse> getAllOrderItems() {
        List<OrderItems> orderItems = orderItemsRepository.findAll();
        return orderItems.stream().map(ViewOrderItemsResponse::new).collect(Collectors.toList());
    }
}
