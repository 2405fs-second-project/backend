//package com.second.backend.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@RestController
//@RequestMapping("/api/auth")
//public class ValidateTokenController {
//
//    @PostMapping("/validateToken")
//    public ResponseEntity<?> validateToken() {
//        // 세션에서 사용자 정보를 확인하여 유효성 검사
//        boolean isValid = validateSession();
//        return ResponseEntity.ok(new TokenResponse(isValid));
//    }
//
//    private boolean validateSession() {
//        // 세션에서 유저 정보를 확인하는 로직
//        // 예시로 true 반환, 실제 구현에 따라 세션 검증 로직 필요
//        return true; // 세션이 유효하면 true 반환
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    static class TokenResponse {
//        private boolean isValid;
//    }
//}
