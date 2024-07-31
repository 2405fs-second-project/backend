package com.second.backend.controller;

import com.second.backend.dto.ViewOrderItemsResponse;
import com.second.backend.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemsController {

    @Autowired
    private OrderItemsService orderItemsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ViewOrderItemsResponse>> getOrderItemsByUserId(@PathVariable Integer userId) {
        List<ViewOrderItemsResponse> orderItems = orderItemsService.getOrderItemsByUserId(userId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/")
    public ResponseEntity<List<ViewOrderItemsResponse>> getAllOrderItems() {
        List<ViewOrderItemsResponse> orderItems = orderItemsService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }
}
