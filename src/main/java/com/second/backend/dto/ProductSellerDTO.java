package com.second.backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSellerDTO {
    private Integer productid;
    private Integer sellerId;
    private String gender;
    private String kind;
    private String name;
    private String color;
    private String fullname;
    private String code;
    private String fileurl;
    private LocalDate delisteddate;
    private String description;
    private Integer price;
    private Map<String, Integer> sizesStock;
}
