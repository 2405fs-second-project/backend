package com.second.backend.service;
import com.second.backend.model.Product;
import com.second.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.util.List;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 업로드 디렉토리 경로
    private static final String UPLOAD_DIR = "uploads/";

    // 제품 목록 조회 메서드
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 제품 저장 메서드
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 파일 저장 메서드
    public String saveImageFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // 업로드할 디렉토리 경로 생성
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 디렉토리가 존재하지 않으면 생성
            }

            // 파일의 실제 경로 설정
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);

            // 파일을 저장하고 경로를 URL로 반환
            Files.write(path, file.getBytes());
            return "/api/products/image/" + fileName; // 예시 URL 반환
        }
        return null; // 파일이 없을 경우 null 반환
    }

    // 이미지 다운로드 기능
    public Resource downloadFile(String fileUrl) throws IOException {
        Path path = Paths.get(UPLOAD_DIR + fileUrl);
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("Could not read file: " + fileUrl);
        }
        return resource;
    }
}
