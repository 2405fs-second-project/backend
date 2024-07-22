package com.github.userpage.web.dto;

import com.github.userpage.model.Product;
import com.github.userpage.model.Users;
import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewProductResponse {

    private Integer id;
    private String category_gender;
    private String name;
    private String color;
    private String fullname;
    private String code;
    private Integer stock;
    private String file;
    private Data listed_date;
    private String description;
    private Integer price;
    private Users seller;

    public ViewProductResponse(Product product) {
        this.id = product.getId();
        this.category_gender = product.getCategory_gender();
        this.name = product.getName();
        this.color = product.getColor();
        this.fullname = product.getFullname();
        this.code = product.getCode();
        this.stock = product.getStock();
        this.file = product.getFile();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.seller = product.getSeller();
    }
}
