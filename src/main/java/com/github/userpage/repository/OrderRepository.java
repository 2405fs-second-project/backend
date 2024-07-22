package com.github.userpage.repository;

import com.github.userpage.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer userId);
}
