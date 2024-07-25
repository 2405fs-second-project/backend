package com.second.backend.repository;

import com.second.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional; //추후 삭제 필요_cartService와 연결되어있음

public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findByGender(String gender);
    List<Product> findByGenderAndKind(String gender, String kind);
    List<Product> findProductById(Integer Id);
    List<Product> findProductByUsersId(Integer id);
    Optional<Product> findById(Integer userId);//추후 삭제 필요_cartService와 연결되어있음
}
