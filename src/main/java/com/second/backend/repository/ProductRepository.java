package com.second.backend.repository;
import com.second.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    Optional<Product> findById(Integer id);

    List<Product> findByName(String name);

    List<Product> findByGenderAndKind(String gender, String kind);
}
