package com.second.backend.dto;

import com.second.backend.model.Product;
import lombok.*;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewProductResponse {

    private Integer id;
    private String gender;
    private String kind;
    private String name;
    private String color;
    private String fullName;
    private String code;
    private Integer stock;
    private Integer price;
    private String fileUrl;
    private String description;
    private Integer sellerId;
    private LocalDate listedDate;

    public ViewProductResponse(Product product) {
        this.id = product.getId();
        this.gender = product.getGender();
        this.kind = product.getKind();
        this.name = product.getName();
        this.color = product.getColor();
        this.fullName = product.getFullName();
        this.code = product.getCode();
        this.stock = product.getStock();
        this.price = product.getPrice();
        this.fileUrl = product.getFileUrl();
        this.description = product.getDescription();
        this.sellerId = product.getSeller_id();
        this.listedDate = product.getListedDate();
    }
}
