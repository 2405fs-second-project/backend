package com.second.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class CartItemUpdateRequest {
    private Integer itemId;    // 장바구니 항목의 ID
    private Integer quantity;  // 업데이트할 수량
}
