package com.second.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.second.backend.model.Carts;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Integer> {
//    Carts findByUserId(Integer userId);
   Optional<Carts> findByUserId(Integer userId);
   Carts findByCartUserId(Integer userId);
   // 사용자 ID로 장바구니 아이템을 삭제하는 메소드 추가
   void deleteByUserId(Integer userId);
}
