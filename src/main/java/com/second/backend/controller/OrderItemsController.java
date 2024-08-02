package com.second.backend.controller;

import com.second.backend.service.OrderItemsService;
import com.second.backend.dto.ViewOrderItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    @Value("${file.upload-products}")
    private String uploadDir;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ViewOrderItemsResponse>> getOrderItemsByUserId(@PathVariable Integer userId) {
        try {
            List<ViewOrderItemsResponse> orderItems = orderItemsService.getOrderItemsByUserId(userId);
            if (orderItems.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ViewOrderItemsResponse>> getAllOrderItems() {
        try {
            List<ViewOrderItemsResponse> orderItems = orderItemsService.getAllOrderItems();
            if (orderItems.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // 파일 경로 생성 및 정상화
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();

            // 이미지 경로에서 "/img/" 제거
            Path relativePath = Paths.get(filename).normalize();

            // 실제 파일 경로 생성
            Path fullPath = Paths.get(uploadDir).resolve(relativePath);

            Resource resource = new UrlResource(fullPath.toUri());

            // 파일 존재 여부 및 읽기 가능 여부 확인
            if (resource.exists() && resource.isReadable()) {
                // 파일 MIME 타입 설정 (파일 확장자에 따라 동적으로 설정 가능)
                String contentType = Files.probeContentType(fullPath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, contentType)
                        .body(resource);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            // IOException 발생 시 서버 에러 응답
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}