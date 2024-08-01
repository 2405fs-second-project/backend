package com.second.backend.repository;

import com.second.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import com.second.backend.model.Carts;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Integer> {
   Optional<Carts> findByUsers(Users users);
   Carts findByUsers_Id(Integer id);
   // 사용자 ID로 장바구니 아이템을 삭제하는 메소드 추가
   void deleteByUsers(Users users);
}
