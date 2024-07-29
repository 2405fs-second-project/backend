package com.second.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsResponse { //전체 장바구니 정보를 담고 있고
    private Integer cartId; // 장바구니 ID
    private List<CartItemsResponse> cartItems; // 장바구니 아이템 리스트
}
