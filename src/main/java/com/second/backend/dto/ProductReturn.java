package com.second.backend.dto;

public class ProductReturn {

    private String category_gender;
    private String category_kind;
    private String name;
    private String color;
    private String fullname;
    private String code;
    private Integer stock;
    private Integer price;
    private String fileUrl;;


    public ProductReturn() {
    }

    public String getCategory_gender() {
        return category_gender;
    }

    public void setCategory_gender(String category_gender) {
        this.category_gender = category_gender;
    }

    public String getCategory_kind() {
        return category_kind;
    }

    public void setCategory_kind(String category_kind) {
        this.category_kind = category_kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


}
