//package com.second.backend.service;
//import com.second.backend.model.Product;
//import com.second.backend.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Value("${file.upload-dir}")
//    private String uploadDir;
//
//    // 제품 목록 조회 메서드
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//    // 특정 ID로 제품 조회 메서드
//    public Optional<Product> findProductById(Integer id) {
//        return productRepository.findById(id);
//    }
//    public Product getProductById(Integer productId) {
//        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
//    }
//
//    // 제품 저장 메서드
//    public Product saveProduct(Product product) {
//        return productRepository.save(product);
//    }
//
//
//    // 파일 저장 메서드
//    public String storeFile(MultipartFile file) throws IOException {
//        // 상대 경로를 절대 경로로 변환
//        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
//
//        // 디렉토리가 없으면 생성
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        // 파일 저장
//        String filename = file.getOriginalFilename();
//        Path filePath = uploadPath.resolve(filename);
//        file.transferTo(filePath.toFile());
//
//        // 파일의 URL 반환 (웹서버의 파일 접근 경로)
//        return "/img/" + filename;
//    }
//
//    // 특정 물품명 검색
//    public List<Product> searchByName(String name) {
//        return productRepository.findByName(name);
//    }
//
//    // 특정 카테고리로 검색
//    public List<Product> findProductsByCategory(String gender, String kind) {
//        return productRepository.findByGenderAndKind(gender, kind);
//    }
//
//
//
//    // 쇼핑몰 조회하기 할 때 여성, 남성, 악세서리 모두 다 똑같이 물품 보여주기
//    public List<Product> getAll(boolean includeOutOfStock) {
//        List<String> categories = List.of("여성", "남성", "악세사리");
//        return productRepository.findAll().stream()
//                .filter(product -> categories.contains(product.getCategory()) &&
//                        (includeOutOfStock || product.getStock() > 0))
//                .collect(Collectors.toList());
//    }
//}