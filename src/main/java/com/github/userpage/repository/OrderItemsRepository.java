package com.github.userpage.repository;

import com.github.userpage.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    @Query("SELECT oi FROM OrderItems oi JOIN oi.order o WHERE o.user.id = :userId")
    List<OrderItems> findByUserId(@Param("userId") Integer userId);
}
