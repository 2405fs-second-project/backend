package com.second.backend.repository;

import com.second.backend.model.CartItems;
import com.second.backend.model.Carts;
import com.second.backend.model.Product;
import com.second.backend.model.ProductSizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    Optional<CartItems> findByCartAndProductAndProductSizes(Carts cart, Product product, ProductSizes productSizes);
    List<CartItems> findByCart(Carts cart);
    List<CartItems> findByUserId(Integer userId);
    CartItems findByCartIdAndProductIdAndItemSizeId(Integer cartid,Integer productid,Integer productsizeid);

}
