package com.second.backend.service;

import com.second.backend.model.OrderItems;
import com.second.backend.repository.OrderItemsRepository;
import com.second.backend.dto.ViewOrderItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional(readOnly = true)  // 데이터 일관성을 유지하는데 도움이 되는 것
    public List<ViewOrderItemsResponse> getOrderItemsByUserId(Integer userId) {
        List<OrderItems> orderItems = orderItemsRepository.findByOrderUserId(userId);
        return orderItems.stream()
                .map(orderItem -> new ViewOrderItemsResponse(
                        orderItem.getOrder().getId(),
                        orderItem.getOrder().getOrderNumber(),
                        orderItem.getOrder().getOrderDate(),
                        orderItem.getProduct().getId(),
                        orderItem.getProduct().getName(),
                        orderItem.getProduct().getPrice(),
                        orderItem.getProduct().getFileUrl().replace("/img/",""), // 이미지 URL 추가
                        orderItem.getQuantity(),
                        orderItem.getPayState()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ViewOrderItemsResponse> getAllOrderItems() {
        List<OrderItems> orderItems = orderItemsRepository.findAll();
        return orderItems.stream()
                .map(orderItem -> new ViewOrderItemsResponse(
                        orderItem.getOrder().getId(),
                        orderItem.getOrder().getOrderNumber(),
                        orderItem.getOrder().getOrderDate(),
                        orderItem.getProduct().getId(),
                        orderItem.getProduct().getName(),
                        orderItem.getProduct().getPrice(),
                        orderItem.getProduct().getFileUrl(), // 상품 파일 정보 가져오기
                        orderItem.getQuantity(),
                        orderItem.getPayState()
                ))
                .collect(Collectors.toList());
    }
}