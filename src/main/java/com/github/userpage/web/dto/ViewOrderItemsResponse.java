package com.github.userpage.web.dto;

import com.github.userpage.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ViewOrderItemsResponse {
    private final Integer order_id;        // 주문 ID
    private final String order_number;     // 주문 번호 : order에서 가져옴
    private final LocalDateTime orderDate; // 주문 날짜 : order에서 가져옴
    private final Integer product_id;      // 상품 ID : product에서 가져옴
    private final String productName;      // 상품 이름 : product에서 가져옴
    private final Integer productPrice;    // 상품 가격 : product에서 가져옴
    private final String productFile;      // 상품 파일 : product에서 가져옴
    private final Integer quantity;        // 수량
    private final String pay_state;  //주문 상태

    public ViewOrderItemsResponse(OrderItems orderItems) {
        this.order_id = orderItems.getOrder().getId(); // 주문 ID
        this.order_number = orderItems.getOrder().getOrder_number(); // 주문 번호
        this.orderDate = orderItems.getOrder().getOrder_date();
        this.product_id = orderItems.getProduct().getId(); // 상품 ID
        this.productName = orderItems.getProduct().getName(); // 상품 이름
        this.productPrice = orderItems.getProduct().getPrice();
        this.productFile = orderItems.getProduct().getFile();
        this.quantity = orderItems.getQuantity(); // 수량
        this.pay_state = orderItems.getPay_state(); // 단가
    }
}
