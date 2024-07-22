package com.second.backend.repository;

import com.second.backend.model.ProductSizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSizesRepository extends JpaRepository<ProductSizes, Integer> {
    ProductSizes findByProductIdAndSize(Integer productId, String size);
    Optional<ProductSizes> findById(Integer id);
}
