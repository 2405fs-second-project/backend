package com.second.backend.model;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //JPA Entity 클래스임을 나타내는 어노테이션입니다.
@Table(name = "product") //데이터베이스에서 매핑할 테이블 이름을 지정합니다.
@Data // 롬복 애노테이션: Getter, Setter, toString, hashCode 등을 자동 생성합니다.
@NoArgsConstructor // 롬복 애노테이션: 매개변수 없는 생성자를 자동 생성합니다.
public class Product {
    @Id //엔티티의 주키(PK) 필드를 나타내며, @GeneratedValue로 자동 생성 전략을 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_gender", length = 10, nullable = false)
    private String gender;

    @Column(name = "category_kind", length = 20, nullable = false)
    private String kind;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "color", columnDefinition = "text", nullable = false)
    private String color;

    @Column(name = "fullname", columnDefinition = "text", nullable = false)
    private String fullName;

    @Column(name = "code", columnDefinition = "text", nullable = false)
    private String code;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "file", length = 255, nullable = false)
    private String fileUrl;// 이미지 URL 저장

    @Column(name = "listed_date")
    private LocalDate listedDate;

    @Column(name = "description", columnDefinition = "text")
    private String description;


    @Column(name = "seller_id")
    private Integer seller_id;

    @Column(name = "category", length = 50, nullable = false)
    private String category;


    @PrePersist
    public void prePersist() {
        this.listedDate = LocalDate.now(); // 저장 전에 listedDate를 현재 날짜로 설정
    }
    public Product(Integer id, String gender, String kind, String name, String color, String fullName, String code, Integer stock, String fileUrl, LocalDate listedDate, String description, Integer price, Integer seller_id) {
        this.id = id;
        this.gender = gender;
        this.kind = kind;
        this.name = name;
        this.color = color;
        this.fullName = fullName;
        this.code = code;
        this.stock = stock;
        this.fileUrl = fileUrl;
        this.listedDate = listedDate;
        this.description = description;
        this.price = price;
        this.seller_id = seller_id;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public LocalDate getListedDate() {
        return listedDate;
    }

    public void setListedDate(LocalDate listedDate) {
        this.listedDate = listedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Integer seller_id) {
        this.seller_id = seller_id;
    }

    public String getCategory() {
        return category;
    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
}
