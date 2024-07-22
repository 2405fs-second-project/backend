package com.second.backend.repository;
import com.second.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    // 특정 물품명 검색
    List<Product> findByName(String name);

    // 특정 물품명과 재고 조건 검색
    List<Product> findByNameAndStock(String name, int i);

    // 조회 수에 따라 재배열하여 조회
    List<Product> findByCategory(String category);
    List<Product> findByCategoryAndStock(String category, int i);


}
