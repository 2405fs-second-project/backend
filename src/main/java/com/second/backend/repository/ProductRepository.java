package com.second.backend.repository;
import com.second.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    Optional<Product> findById(Integer id);
}
