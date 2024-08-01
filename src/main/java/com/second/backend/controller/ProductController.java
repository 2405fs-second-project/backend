package com.second.backend.controller;

import com.second.backend.dto.ProductDetailResponse;
import com.second.backend.dto.ProductResponse;
import com.second.backend.dto.ProductReturn;
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

    @GetMapping("/{gender}")// 남성,여성,악세사리(영어로)
    public ResponseEntity<Slice<ProductResponse>> getProductsByGender(
            @PathVariable String gender,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Slice<ProductResponse> products = productService.findProductsByGender(gender, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{gender}/{kind}")//자켓, 바지, 스웨터
    public ResponseEntity<Slice<ProductResponse>> getProductsByGenderAndKind(
            @PathVariable String gender,
            @PathVariable String kind,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ProductResponse> products = productService.findProductsByGenderAndKind(gender, kind, pageable );
        return ResponseEntity.ok(products);
    }
    @GetMapping("/detail/{productid}")//제품 상세 화면 데이터
    public ResponseEntity<List<ProductDetailResponse>> getProductsDetailById(@PathVariable Integer productid) {List<ProductDetailResponse> products = productService.findProductsDetailById(productid);
        return ResponseEntity.ok(products);}

    // 특정 물품명 검색
    @GetMapping("/search")
    public ResponseEntity<List<ProductReturn>> searchByName(@RequestParam String name) {
        List<ProductReturn> products = productService.searchByNameInternal(name);
        return ResponseEntity.ok(products);
    }

    // 특정 카테고리로 검색
    @GetMapping("/category")
    public ResponseEntity<List<ProductReturn>> searchByGenderAndKind(@RequestParam String gender,
                                                                     @RequestParam String kind) {
        List<ProductReturn> products = productService.searchByGenderAndKind(gender, kind);
        return ResponseEntity.ok(products);
    }
}




