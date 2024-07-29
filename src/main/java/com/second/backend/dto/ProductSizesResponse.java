package com.second.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizesResponse {
    private Integer id;
    private Integer productId;
    private String size;
    private Integer stock;
}
