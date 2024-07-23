package com.second.backend.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Carts carts;

    @ManyToOne
    @JoinColumn(name = "Product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_size_id")
    private ProductSizes productSizes;

    @Column(name = "user_id", nullable = false)
    private int userId;

    public CartItems() {

    }

    public ProductSizes getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(ProductSizes productSizes) {
        this.productSizes = productSizes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Carts getCarts() {
        return carts;
    }

    public void setCarts(Carts carts) {
        this.carts = carts;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
