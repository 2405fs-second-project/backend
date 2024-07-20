package com.second.backend.service;
import com.second.backend.model.Product;
import com.second.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 제품 목록 조회 메서드
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 제품 저장 메서드
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 특정 ID로 제품 조회 메서드
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }
    // 파일 저장 메서드
    public String storeFile(MultipartFile file) throws IOException {
        // 상대 경로를 절대 경로로 변환
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 저장
        String filename = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        file.transferTo(filePath.toFile());

        // 파일의 URL 반환 (웹서버의 파일 접근 경로)
        return "/img/" + filename;
    }


}
