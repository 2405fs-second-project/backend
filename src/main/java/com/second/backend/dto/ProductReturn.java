package com.second.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReturn {

    private String category_gender;
    private String category_kind;
    private String name;
    private String color;
    private String fullname;
    private String code;
    private Integer price;
    private String fileUrl;;
}
