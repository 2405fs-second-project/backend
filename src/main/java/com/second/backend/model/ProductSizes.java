package com.second.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_sizes")

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class ProductSizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Product_id")
    private Product product;

    @Column(name = "option_size", length = 50)
    private String size;

    @Column(name = "stock", nullable = false)
    private Integer stock;


}
