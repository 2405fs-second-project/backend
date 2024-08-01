package com.second.backend.repository;

import com.second.backend.model.Product;
import com.second.backend.model.ProductSizes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductSizesRepository extends JpaRepository<ProductSizes, Integer> {

    Optional<ProductSizes> findById(Integer id);
    ProductSizes findByCartSizeIde(Integer id);
    Optional<ProductSizes> findOptionalByProductIdAndSize(Integer productId, String size);
    List<ProductSizes> findSizesByProductId(Integer productid);
    List<ProductSizes> findByProductIdIn(List<Integer> productids);
    List<ProductSizes> findByProductIdAndSize(Integer productId, String size);
    boolean existsByProductId(Integer productid);
    void deleteByProductId(Integer productId);

}
