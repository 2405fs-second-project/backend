package com.second.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name="orders")
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "total_price", nullable = false)
    private Integer total_price;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime order_date;

    @Column(name = "order_number", nullable = false, length = 100)
    private String order_number;

    @OneToMany(mappedBy = "order")
    private Set<OrderItems> orderItems;

    public Integer getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public String getOrder_number() {
        return order_number;
    }

    public Set<OrderItems> getOrderItems() {
        return orderItems;
    }
}
