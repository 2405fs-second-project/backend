package com.second.backend.service;

import com.second.backend.dto.ProductDetailResponse;
import com.second.backend.dto.ProductResponse;
import com.second.backend.model.Product;
import com.second.backend.model.ProductSizes;
import com.second.backend.repository.ProductRepository;
import com.second.backend.repository.ProductSizesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSizesRepository productSizesRepository;

    //1. gender별 물품 조회 메서드
    public List<ProductResponse> findProductsByGender(String gender) {
        List<Product> products = productRepository.findByGender(gender);

        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getColor(),
                        product.getPrice(),
                        product.getFileUrl()))
                .collect(Collectors.toList());
    }

    //2.gender-kind별 물품 조회 메서드
    public List<ProductResponse> findProductsByGenderAndKind(String gender, String kind) {
        List<Product> products = productRepository.findByGenderAndKind(gender, kind);

        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getColor(),
                        product.getPrice(),
                        product.getFileUrl()))
                .collect(Collectors.toList());
    }

    //3.물품상세 조회 메서드
    public List<ProductDetailResponse> findProductsDetailById(Integer productid) {
        List<Product> products = productRepository.findProductById(productid);
        List<ProductSizes> productSizes =productSizesRepository.findSizesByProductId(productid);
        return products.stream()
                .map(product -> {
                    List<String> sizes = productSizes.stream()
                            .filter(size -> size.getProduct().getId().equals(productid))
                            .map(ProductSizes::getSize)
                            .collect(Collectors.toList());

                    return new ProductDetailResponse(
                            product.getId(),
                            product.getName(),
                            product.getColor(),
                            product.getFullName(),
                            product.getCode(),
                            product.getPrice(),
                            product.getFileUrl(),
                            product.getDescription(),
                            sizes
                    );
                })
                .collect(Collectors.toList());
    }
}