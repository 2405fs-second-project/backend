package com.second.backend.controller;
import com.second.backend.model.Product;
import java.util.Base64;
import org.springframework.http.HttpHeaders;
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
    private static final String UPLOAD_DIR = "uploads/";

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
    @PostMapping
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
            String imageUrl = productService.saveImageFile(file);

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
    private String saveImageFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            Path path = Paths.get(filePath);

            Files.write(path, file.getBytes());
            return "/api/products/image/" + fileName;
        }
        return null;
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<String> getImage(@PathVariable String filename) throws IOException {
        String filePath = UPLOAD_DIR + filename;
        String base64Image = encodeImageToBase64(filePath);

        if (base64Image != null) {
            String mimeType = Files.probeContentType(Paths.get(filePath)); // MIME 타입을 동적으로 결정
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, mimeType != null ? mimeType : "image/jpeg")
                    .body("data:" + (mimeType != null ? mimeType : "image/jpeg") + ";base64," + base64Image);
        } else {
            throw new IOException("Could not read file: " + filename);
        }
    }

    private String encodeImageToBase64(String filePath) {
        try {
            Path path = Paths.get(filePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(imageBytes); // Java 표준 Base64 사용
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}

