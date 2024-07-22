package com.second.backend.controller;
import com.second.backend.model.Product;
import com.second.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.second.backend.utils.ResourceNotFoundException;
import com.second.backend.dto.ProductDetailResponse;


@RestController
@RequestMapping("/products/detail")
public class ProductDetailController {
    @Autowired
    private ProductService productService;
    @GetMapping("/{id}")
    public ProductDetailResponse getProductById(@PathVariable Integer id) {
        // Optional에서 값을 추출하거나 예외를 던지도록 처리
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return convertToProductDetailResponse(product);
    }
    // Product 객체를 ProductDetailResponse로 변환
    private ProductDetailResponse convertToProductDetailResponse(Product product) {
        ProductDetailResponse response = new ProductDetailResponse();
        response.setId(product.getId());
        response.setCatekind(product.getKind());
        response.setName(product.getName());
        response.setColor(product.getColor());
        response.setFullname(product.getFullName());
        response.setCode(product.getCode());
        response.setPrice(product.getPrice());
        response.setFileUrl(product.getFileUrl());
        response.setDescription(product.getDescription());

        String filePath = product.getFileUrl().replace("/img/", "");
        response.setFileUrl(filePath);
        return response;
    }
}
