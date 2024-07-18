package com.second.backend.dto;
import org.springframework.web.multipart.MultipartFile;

public class ProductReturn {

    private String category_gender; // 클라이언트에서는 category_gender로 전송됨
    private String category_kind;   // 클라이언트에서는 category_kind로 전송됨
    private String name;            // 클라이언트에서는 name으로 전송됨
    private String color;           // 클라이언트에서는 color로 전송됨
    private String fullname;        // 클라이언트에서는 fullname으로 전송됨
    private String code;            // 클라이언트에서는 code로 전송됨
    private Integer stock;          // 클라이언트에서는 stock으로 전송됨
    private Integer price;          // 클라이언트에서는 price로 전송됨
    private MultipartFile file;    // 클라이언트에서는 file로 전송됨 (이미지 파일을 받기 위한 필드)


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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


}
