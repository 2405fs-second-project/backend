package com.second.backend.controller;

import com.second.backend.service.OrderItemsService;
import com.second.backend.dto.ViewOrderItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ViewOrderItemsResponse>> getOrderItemsByUserId(@PathVariable Integer userId) {
        try {
            List<ViewOrderItemsResponse> orderItems = orderItemsService.getOrderItemsByUserId(userId);
            if (orderItems.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ViewOrderItemsResponse>> getAllOrderItems() {
        try {
            List<ViewOrderItemsResponse> orderItems = orderItemsService.getAllOrderItems();
            if (orderItems.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
