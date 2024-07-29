package com.second.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartRequest {
    private Integer userId;
    private Integer productId;
    private String size;
    private Integer quantity;

}
