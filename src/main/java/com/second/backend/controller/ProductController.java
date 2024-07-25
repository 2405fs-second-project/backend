package com.second.backend.controller;

import com.second.backend.dto.ProductDetailResponse;
import com.second.backend.dto.ProductResponse;
import com.second.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{gender}")
    public ResponseEntity<List<ProductResponse>> getProductsByGender(@PathVariable String gender) {
        List<ProductResponse> products = productService.findProductsByGender(gender);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{gender}/{kind}")
    public ResponseEntity<List<ProductResponse>> getProductsByGenderAndKind(
            @PathVariable String gender,
            @PathVariable String kind) {
        List<ProductResponse> products = productService.findProductsByGenderAndKind(gender, kind);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/detail/{productid}")
    public ResponseEntity<List<ProductDetailResponse>> getProductsDetailById(@PathVariable Integer productid) {List<ProductDetailResponse> products = productService.findProductsDetailById(productid);
        return ResponseEntity.ok(products);}



}




