package com.second.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.second.backend.model.CartItems;
import com.second.backend.model.Carts;
import com.second.backend.model.Product;
import com.second.backend.model.ProductSizes;
import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer>{
    List<CartItems> findByUserId(Integer userId);
    CartItems findByCartsAndProductAndProductSizes(Carts carts, Product product, ProductSizes productSizes);
    List<CartItems> findByCarts(Carts carts);
}
