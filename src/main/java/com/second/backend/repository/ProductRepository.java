package com.second.backend.repository;

import com.second.backend.model.Product;
import com.second.backend.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional; //추후 삭제 필요_cartService와 연결되어있음

public interface ProductRepository extends JpaRepository<Product, Integer>{
    Slice<Product> findByGenderAndKind(String gender, String kind, Pageable pageable);
    Slice<Product> findByGender(String gender, Pageable pageable);

    Page<Product> findByUsersId(Integer sellerId, Pageable pageable);

    List<Product> findProductById(Integer Id);
    Optional<Product> findById(Integer userId);//추후 삭제 필요_cartService와 연결되어있음 -> 특정 상품 Id 로 단일 상품 조회

    List<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE "
            + "(:gender IS NULL OR p.gender = :gender) AND "
            + "(:kind IS NULL OR p.kind = :kind)")
    List<Product> findByCategory(@Param("gender") String gender, @Param("kind") String kind);

}
