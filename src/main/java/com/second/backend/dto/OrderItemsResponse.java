package com.second.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsResponse {
    private Integer productId;
    private String productName;
    private String productImage; // 파일 URL로 이미지 URL을 저장
    private String productColor;
    private String productSize;
    private Integer quantity;
    private Integer price;
}
