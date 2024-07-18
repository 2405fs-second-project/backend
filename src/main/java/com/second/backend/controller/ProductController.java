package com.second.backend.controller;
import com.second.backend.model.Product;
import com.second.backend.dto.ProductRequest;
import com.second.backend.dto.ProductReturn;
import com.second.backend.utils.ProductMapper;
import com.second.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // 모든 제품 목록 조회
    @GetMapping("/products")
    public ResponseEntity<List<ProductReturn>> getAllProducts(@RequestParam(required = false) List<String> fields) {
        List<Product> products = productService.getAllProducts();

        // 모든 필드를 반환하도록 요청이 없는 경우
        if (fields == null || fields.isEmpty()) {
            List<ProductReturn> ProductReturns = convertToProductReturnList(products);
            return ResponseEntity.ok(ProductReturns);
        } else {
            List<ProductReturn> ProductReturns = convertToProductReturnListWithSelectedFields(products, fields);
            return ResponseEntity.ok(ProductReturns);
        }
    }
    // 이미지 업로드 디렉토리 경로
    private static String UPLOAD_DIR = "uploads/";

    // 제품 저장
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            // 이미지 파일을 업로드하고 파일의 URL을 생성
            String imageUrl = saveImageFile(productRequest.getFile());
            // 현재 날짜로 listed_date 설정
            LocalDate today = LocalDate.now();

            // Product 객체 생성 및 데이터 설정
            Product product = new Product();
            product.setGender(productRequest.getCategory_gender());
            product.setKind(productRequest.getCategory_kind());
            product.setName(productRequest.getName());
            product.setColor(productRequest.getColor());
            product.setFullName(productRequest.getfullname());
            product.setCode(productRequest.getCode());
            product.setStock(productRequest.getStock());
            product.setPrice(productRequest.getPrice());
            product.setFileUrl(imageUrl); // 이미지 URL 설정
            product.setListedDate(today); // 오늘 날짜로 설정
            product.setDescription(productRequest.getDescription());

            // ProductService를 통해 Product 저장
            productService.saveProduct(product);

            // 성공적으로 생성되었음을 클라이언트에게 응답
            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // 이미지 파일을 저장하고 URL을 반환하는 메서드
    private String saveImageFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // 업로드할 디렉토리 경로 생성
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 디렉토리가 존재하지 않으면 생성
            }

            // 파일의 실제 경로 설정
            String fileName = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            Path path = Paths.get(filePath);

            // 파일을 저장하고 경로를 URL로 반환
            Files.write(path, file.getBytes());
            return "/api/products/image/" + fileName; // 예시 URL 반환
        }
        return null; // 파일이 없을 경우 null 반환
    }


}

