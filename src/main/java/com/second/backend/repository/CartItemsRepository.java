package com.second.backend.repository;

import com.second.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
//    Optional<CartItems> findByCartAndProductAndProductSizes(Carts cart, Product product, ProductSizes productSizes);
    List<CartItems> findByCart(Carts cart);
    List<CartItems> findByUsers(Users users);
    List<CartItems> findByCartId(Integer cartId);
    List<CartItems> findAllById(Iterable<Integer> ids);
}
