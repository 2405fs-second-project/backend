package com.second.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDetailResponse {

    private Integer productId;
    private String name;
    private String color;
    private String fullname;
    private String code;
    private Integer price;
    private String fileUrl;
    private String description;
    private List<String> sizes;
}
