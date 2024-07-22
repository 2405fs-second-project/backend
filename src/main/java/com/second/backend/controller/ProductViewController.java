package com.second.backend.controller;
import com.second.backend.dto.ProductViewReturn;
import com.second.backend.model.Product;
import com.second.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home")
public class ProductViewController {

    @Autowired
    private ProductService productService;
    private static final String UPLOAD_DIR = "uploads/";
    // 모든 제품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductViewReturn>> getProducts(@RequestParam(required = false) List<String> fields) {
        List<Product> products = productService.getAllProducts();

        // Product 객체를 ProductViewReturn으로 변환
        List<ProductViewReturn> productDTOs = products.stream()
                .map(this::convertToProductViewReturn)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }

    // Product 객체를 ProductViewReturn으로 변환
    private ProductViewReturn convertToProductViewReturn(Product product) {
        ProductViewReturn dto = new ProductViewReturn();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setColor(product.getColor());
        dto.setFullName(product.getFullName());
        dto.setCode(product.getCode());
        dto.setPrice(product.getPrice());
// 이미지 파일의 실제 경로
        String filePath =  product.getFileUrl().replace("/img/", "");
        dto.setFile(filePath);

        return dto;
    }
}