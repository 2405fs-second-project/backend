package com.second.backend.controller;

import com.second.backend.dto.AddUserRequest;
import com.second.backend.dto.UserResponse;
import com.second.backend.model.Users;
import com.second.backend.service.UserService;
import com.second.backend.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AddUserRequest addUserRequest) {
        try {
            userService.registerUser(addUserRequest.toEntity());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        logger.info("Received token for validation: {}", token);
        try {
            if (jwtUtil.isTokenExpired(token)) {
                logger.warn("Token is expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
            }
            String email = jwtUtil.extractEmail(token);
            logger.info("Extracted email from token: {}", email);
            Users user = userService.findByEmail(email);
            logger.info("User found: {}", user.getEmail());
            return ResponseEntity.ok(new UserResponse(user));
        } catch (Exception e) {
            logger.error("Error validating token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error validating token");
        }
    }
}
