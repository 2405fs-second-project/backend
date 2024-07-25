package com.second.backend.repository;


import com.second.backend.model.ProductSizes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductSizesRepository extends JpaRepository<ProductSizes, Integer> {
    List<ProductSizes> findSizesByProductId(Integer productid);
    List<ProductSizes> findByProductIdIn(List<Integer> productids);
    List<ProductSizes> findByProductIdAndSize(Integer productId, String size);
    boolean existsByProductId(Integer productid);
    void deleteByProductId(Integer productId);
}
