package com.second.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Integer id;
    private String fileUrl;
    private String name;
    private String color;
    private String size;
    private Integer quantity;
    private Integer price;
}
