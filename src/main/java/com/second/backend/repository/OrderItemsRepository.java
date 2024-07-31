package com.second.backend.repository;

import com.second.backend.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    List<OrderItems> findByOrderUserId(Integer userId); // 사용자 ID로 주문 항목을 찾는 메서드 수정
}
