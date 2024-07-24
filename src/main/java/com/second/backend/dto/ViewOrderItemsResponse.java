package com.second.backend.dto;

import com.second.backend.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ViewOrderItemsResponse {
    private final Integer orderId;        // 주문 ID
    private final String orderNumber;     // 주문 번호 : order에서 가져옴
    private final LocalDateTime orderDate; // 주문 날짜 : order에서 가져옴
    private final Integer productId;      // 상품 ID : product에서 가져옴
    private final String productName;      // 상품 이름 : product에서 가져옴
    private final Integer productPrice;    // 상품 가격 : product에서 가져옴
    private final String productFileUrl;   // 상품 파일 URL
    private final Integer quantity;        // 수량
    private final String payState;  //주문 상태

    public ViewOrderItemsResponse(OrderItems orderItems) {
        this.orderId = orderItems.getOrder().getId(); // 주문 ID
        this.orderNumber = orderItems.getOrder().getOrder_number(); // 주문 번호
        this.orderDate = orderItems.getOrder().getOrder_date();
        this.productId = orderItems.getProduct().getId(); // 상품 ID
        this.productName = orderItems.getProduct().getName(); // 상품 이름
        this.productPrice = orderItems.getProduct().getPrice();
        this.productFileUrl = orderItems.getProduct().getFileUrl();
        this.quantity = orderItems.getQuantity(); // 수량
        this.payState = orderItems.getPay_state(); // 단가
    }
}
