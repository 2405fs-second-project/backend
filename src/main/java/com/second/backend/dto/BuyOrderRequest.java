package com.second.backend.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyOrderRequest {
    private Integer productId;
    private String productName;
    private String productColor;
    private String productImageUrl; // 필드 이름을 수정했습니다
    private Integer productPrice;
    private Integer quantity;
    private String productSize; // 추가된 필드
    private LocalDate productListedDate;
}
