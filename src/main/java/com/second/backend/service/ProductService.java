package com.second.backend.service;

import com.second.backend.dto.ProductDetailResponse;
import com.second.backend.dto.ProductResponse;
import com.second.backend.model.Product;
import com.second.backend.model.ProductSizes;
import com.second.backend.repository.ProductRepository;
import com.second.backend.repository.ProductSizesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSizesRepository productSizesRepository;

    //1. gender별 물품 조회 메서드
    public Slice<ProductResponse> findProductsByGender(String gender, Pageable pageable) {
        Slice<Product> productSlice = productRepository.findByGender(gender, pageable);

        return productSlice.map(product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getColor(),
                product.getPrice(),
                product.getFileUrl()));
    }


    //2.gender-kind별 물품 조회 메서드
    public Slice<ProductResponse> findProductsByGenderAndKind(String gender, String kind, Pageable pageable) {
        Slice<Product> productSlice = productRepository.findByGenderAndKind(gender, kind, pageable);

        return productSlice.map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getColor(),
                        product.getPrice(),
                        product.getFileUrl()));
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