package com.second.backend.service;

import com.second.backend.model.OrderItems;
import com.second.backend.repository.OrderItemsRepository;
import com.second.backend.dto.ViewOrderItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    public List<ViewOrderItemsResponse> getOrderItemsByUserId(Integer userId) {
        List<OrderItems> orderItems = orderItemsRepository.findByUserId(userId);
        return orderItems.stream()
                .map(orderItem -> new ViewOrderItemsResponse(
                        orderItem.getOrder().getId(),
                        orderItem.getOrder().getOrder_number(),
                        orderItem.getOrder().getOrder_date(),
                        orderItem.getProduct().getId(),
                        orderItem.getProduct().getName(),
                        orderItem.getProduct().getPrice(),
                        orderItem.getProduct().getFileUrl(), // 상품 파일 정보 가져오기
                        orderItem.getQuantity(),
                        orderItem.getPay_state()
                ))
                .collect(Collectors.toList());
    }

    public List<ViewOrderItemsResponse> getAllOrderItems() {
        List<OrderItems> orderItems = orderItemsRepository.findAll();
        return orderItems.stream()
                .map(orderItem -> new ViewOrderItemsResponse(
                        orderItem.getOrder().getId(),
                        orderItem.getOrder().getOrder_number(),
                        orderItem.getOrder().getOrder_date(),
                        orderItem.getProduct().getId(),
                        orderItem.getProduct().getName(),
                        orderItem.getProduct().getPrice(),
                        orderItem.getProduct().getFileUrl(), // 상품 파일 정보 가져오기
                        orderItem.getQuantity(),
                        orderItem.getPay_state()
                ))
                .collect(Collectors.toList());
    }
}
