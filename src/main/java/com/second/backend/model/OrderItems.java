package com.second.backend.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="order_items")
@Entity
public class OrderItems {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "pay_state", length = 50, nullable = false)
    private String payState = "결제완료";

}
