package com.second.backend.controller;
import com.second.backend.model.Product;
import com.second.backend.dto.ProductReturn;
import com.second.backend.utils.ProductMapper;
import com.second.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@RestController
@RequestMapping("/products/list")
public class ProductController {
    @Autowired
    private ProductService productService;

    // 모든 제품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductReturn>> getAllProducts(@RequestParam(required = false) List<String> fields) {
        List<Product> products = productService.getAllProducts();

        // 모든 필드를 반환하도록 요청이 없는 경우
        if (fields == null || fields.isEmpty()) {
            List<ProductReturn> productReturns = ProductMapper.convertToProductReturnList(products);
            return ResponseEntity.ok(productReturns);
        } else {
            List<ProductReturn> productReturns = ProductMapper.convertToProductReturnListWithSelectedFields(products, fields);
            return ResponseEntity.ok(productReturns);
        }
    }

    // 제품 저장
    @PostMapping("/post")
    public ResponseEntity<?> createProduct(
            @RequestParam("category_gender") String categoryGender,
            @RequestParam("category_kind") String categoryKind,
            @RequestParam("name") String name,
            @RequestParam("color") String color,
            @RequestParam("fullname") String fullName,
            @RequestParam("code") String code,
            @RequestParam("stock") String stock,
            @RequestParam("price") String price,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "description", required = false) String description){
        try {
            String imageUrl = productService.storeFile(file);
            System.out.println(imageUrl);
            LocalDate today = LocalDate.now();
            Integer stockInt = Integer.parseInt(stock);
            Integer priceInt = Integer.parseInt(price);

            Product product = new Product();
            product.setGender(categoryGender);
            product.setKind(categoryKind);
            product.setName(name);
            product.setColor(color);
            product.setFullName(fullName);
            product.setCode(code);
            product.setStock(stockInt);
            product.setPrice(priceInt);
            product.setFileUrl(imageUrl);
            product.setListedDate(today);
            product.setDescription(description);

            productService.saveProduct(product);

            // JSON 형식으로 응답 반환
            return ResponseEntity.ok().body(new SuccessResponse("Product created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
        }
    }

    // 특정 물품명 검색
    @GetMapping("/search")
    public ResponseEntity<List<ProductReturn>> searchByName(@RequestParam String name) {
        List<Product> products = productService.searchByName(name);
        List<ProductReturn> productReturns = ProductMapper.convertToProductReturnList(products);
        return ResponseEntity.ok(productReturns);
    }

    // 특정 카테고리로 검색
    @GetMapping("/category")
    public ResponseEntity<List<ProductReturn>> searchProductsByCategory(@RequestParam String gender, @RequestParam String kind) {
        List<Product> products = productService.findProductsByCategory(gender, kind);
        List<ProductReturn> productReturns = ProductMapper.convertToProductReturnList(products);
        return ResponseEntity.ok(productReturns);
    }

    // 쇼핑몰 조회하기 할 때 여성, 남성, 악세서리 모두 다 똑같이 물품을 보여주기
    @GetMapping("/all")
    public ResponseEntity<List<ProductReturn>> getAll(
            @RequestParam(required = false, defaultValue = "true") boolean includeOutOfStock) {
        List<Product> products = productService.getAll(includeOutOfStock);
        List<ProductReturn> productReturns = ProductMapper.convertToProductReturnList(products);
        return ResponseEntity.ok(productReturns);
    }

    // 성공 응답 클래스
    public static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // 오류 응답 클래스
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}

