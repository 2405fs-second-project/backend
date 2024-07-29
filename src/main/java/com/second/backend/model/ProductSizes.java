package com.second.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_sizes")
public class ProductSizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Product_id")
    private Product product;

    @Column(name = "option_size", length = 255)
    private String size;


    @Column(name = "stock", nullable = false)
    private Integer stock;

}
