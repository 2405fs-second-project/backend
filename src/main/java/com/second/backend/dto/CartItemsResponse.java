package com.second.backend.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsResponse {
    private Integer productId;
    private String productName;
    private String productFileUrl; // 파일 URL로 이미지 URL을 저장
    private String productColor;
    private String size;
    private Integer quantity;
    private Integer price;
}
