package com.second.backend.model;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.*;

@Entity
@Table(name = "product")

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "SellerId")
    private Users users;

    @Column(name = "CategoryGender", length = 10, nullable = false)
    private String gender;

    @Column(name = "CategoryKind", length = 20, nullable = false)
    private String kind;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @Column(name = "Color", columnDefinition = "text", nullable = false)
    private String color;

    @Column(name = "FullName", columnDefinition = "text", nullable = false)
    private String fullName;

    @Column(name = "Code", columnDefinition = "text", nullable = false)
    private String code;

    @Column(name = "File", length = 255, nullable = false)
    private String fileUrl;// 이미지 URL 저장

    @Column(name = "DelistedDate")
    private LocalDate DelistedDate;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "Price", nullable = false)
    private Integer price;

    @Column(name = "ListedDate")
    private LocalDate listedDate;


    @PrePersist
    public void prePersist() {
        this.listedDate = LocalDate.now();
    }





}
