package com.second.backend.dto;

import com.second.backend.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ViewOrderItemsResponse {
    private final Integer orderId;
    private final String orderNumber;
    private final LocalDateTime orderDate;
    private final Integer productId;
    private final String productName;
    private final Integer productPrice;
    private final String productFileUrl;
    private final Integer quantity;
    private final String payState;

    public ViewOrderItemsResponse(OrderItems orderItems) {
        this.orderId = orderItems.getOrder().getId();
        this.orderNumber = orderItems.getOrder().getOrderNumber();
        this.orderDate = orderItems.getOrder().getOrderDate();
        this.productId = orderItems.getProduct().getId();
        this.productName = orderItems.getProduct().getName();
        this.productPrice = orderItems.getProduct().getPrice();
        this.productFileUrl = orderItems.getProduct().getFileUrl();
        this.quantity = orderItems.getQuantity();
        this.payState = orderItems.getPayState();
    }
}
