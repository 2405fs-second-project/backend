package com.second.backend.service;

import com.second.backend.model.ProductSizes;
import com.second.backend.repository.ProductSizesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSizeService {

    @Autowired
    private ProductSizesRepository productSizesRepository;

    public ProductSizes getSizeById(Integer id) {
        return productSizesRepository.findById(id).orElseThrow(() -> new RuntimeException("Size not found"));
    }
}
