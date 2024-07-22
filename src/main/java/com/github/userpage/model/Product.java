package com.github.userpage.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_gender", nullable = false, length = 10)
    private String category_gender;

    @Column(name = "category_kind", nullable = false, length = 20)
    private String category_kind;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "color", nullable = false, columnDefinition = "TEXT")
    private String color;

    @Column(name = "fullname", nullable = false, columnDefinition = "TEXT")
    private String fullname;

    @Column(name = "code", nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "file", nullable = false, length = 255)
    private String file;

    @Column(name = "listed_date")
    @Temporal(TemporalType.DATE)
    private Date listed_date;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Users seller;
}
