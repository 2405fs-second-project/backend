package com.second.backend.service;
import com.second.backend.model.Product;
import com.second.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 제품 목록 조회 메서드
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 제품 저장 메서드
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 기타 필요한 비즈니스 로직 메서드 추가 가능
}
