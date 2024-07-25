package com.second.backend.controller;

import com.second.backend.dto.ProductDetailResponse;
import com.second.backend.dto.ProductResponse;
import com.second.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{gender}")
    public ResponseEntity<Slice<ProductResponse>> getProductsByGender(
            @PathVariable String gender,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Slice<ProductResponse> products = productService.findProductsByGender(gender, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{gender}/{kind}")
    public ResponseEntity<Slice<ProductResponse>> getProductsByGenderAndKind(
            @PathVariable String gender,
            @PathVariable String kind,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ProductResponse> products = productService.findProductsByGenderAndKind(gender, kind, pageable );
        return ResponseEntity.ok(products);
    }
    @GetMapping("/detail/{productid}")
    public ResponseEntity<List<ProductDetailResponse>> getProductsDetailById(@PathVariable Integer productid) {List<ProductDetailResponse> products = productService.findProductsDetailById(productid);
        return ResponseEntity.ok(products);}



}




