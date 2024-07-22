package com.second.backend.utils;
import com.second.backend.dto.ProductReturn;
import com.second.backend.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
public class ProductMapper {

    // 모든 필드를 포함하는 ProductReturn 리스트로 변환하는 메서드
    public static List<ProductReturn> convertToProductReturnList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::convertToProductReturn)
                .collect(Collectors.toList());
    }

    // 지정된 필드만 포함하는 ProductReturn 리스트로 변환하는 메서드
    public static List<ProductReturn> convertToProductReturnListWithSelectedFields(List<Product> products, List<String> fields) {
        return products.stream()
                .map(product -> convertToProductReturnWithSelectedFields(product, fields))
                .collect(Collectors.toList());
    }

    // Product를 ProductReturn로 변환하는 메서드
    public static ProductReturn convertToProductReturn(Product product) {
        ProductReturn ProductReturn = new ProductReturn();
        ProductReturn.setCategory_gender(product.getGender());
        ProductReturn.setCategory_kind(product.getKind());
        ProductReturn.setName(product.getName());
        ProductReturn.setColor(product.getColor());
        ProductReturn.setFullname(product.getFullName());
        ProductReturn.setCode(product.getCode());
        ProductReturn.setStock(product.getStock());
        ProductReturn.setPrice(product.getPrice());
        ProductReturn.setFileUrl(product.getFileUrl());


        // 필요한 경우 다른 필드도 추가할 수 있음

        return ProductReturn;
    }

    // 지정된 필드만 포함하는 ProductReturn로 변환하는 메서드
    public static ProductReturn convertToProductReturnWithSelectedFields(Product product, List<String> fields) {
        ProductReturn ProductReturn= new ProductReturn();
        if (fields.contains("category_gender")) {
            ProductReturn.setCategory_gender(product.getGender());
        }
        if (fields.contains("category_kind")) {
            ProductReturn.setCategory_kind(product.getKind());
        }
        if (fields.contains("name")) {
            ProductReturn.setName(product.getName());
        }
        if (fields.contains("color")) {
            ProductReturn.setColor(product.getColor());
        }
        if (fields.contains("fullname")) {
            ProductReturn.setFullname(product.getFullName());
        }
        if (fields.contains("code")) {
            ProductReturn.setCode(product.getCode());
        }
        if (fields.contains("stock")) {
            ProductReturn.setStock(product.getStock());
        }
        if (fields.contains("file")) {
            ProductReturn.setFileUrl(product.getFileUrl());
        }
        if (fields.contains("price")) {
            ProductReturn.setPrice(product.getPrice());
        }
        // 필요한 경우 다른 필드도 추가할 수 있음

        return ProductReturn;
    }

}
