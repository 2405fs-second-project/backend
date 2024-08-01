package com.second.backend.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service

public class CommonService {
    @Value("${file.upload-products}")
    private String productsUploadDir;

    public String saveProductImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있거나 null입니다.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("파일 이름이 null입니다.");
        }

        Path filePath = Path.of(productsUploadDir, fileName);
        File fileToSave = filePath.toFile();

        try {
            // 디렉토리 경로가 없다면 생성
            Path parentDir = filePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir); // 디렉토리 생성
            }

            // 파일 저장
            file.transferTo(fileToSave);
        } catch (IOException e) {
            e.printStackTrace(); // 로그에 예외 메시지 출력 (실제 애플리케이션에서는 적절한 로깅 사용)
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e); // 사용자에게 발생한 예외를 던짐
        }

        return "/img/products/" + fileName;
    }
}
