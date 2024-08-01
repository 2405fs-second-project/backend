package com.second.backend.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartDTO {
    private Integer id;
    private Integer itemSizeid;
    private Integer usersid;
    private Integer cartid;
    private Integer itemid;
    private String itemUrl;
    private String itemName;
    private String itemColor;
    private Integer itemPrice;
    private Integer itemQuantity;
    public String itemSize;
}
