package com.second.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponse { //주문 상세 정보를 제공
    private Integer orderId; // 주문 ID
    private List<OrderItemsResponse> orderItems; // 주문 아이템 리스트
    private Integer totalPrice; // 총 가격
}
