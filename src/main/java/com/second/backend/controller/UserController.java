package com.second.backend.controller;

import com.second.backend.dto.AddUserRequest;
import com.second.backend.dto.UserResponse;
import com.second.backend.model.Users;
import com.second.backend.model.LoginRequest;
import com.second.backend.model.AuthenticationResponse;
import com.second.backend.service.AuthService;
import com.second.backend.service.UserService;
import com.second.backend.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private final AuthService authService;

    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AddUserRequest addUserRequest) {
        try {
            Users user = addUserRequest.toEntity();
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        logger.info("로그인 요청: {}", loginRequest.getEmail());
        try {
            AuthenticationResponse response = authService.authenticate(loginRequest);
            logger.info("로그인 성공: {}", response.getJwt());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("로그인 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        try {
            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
            }
            String email = jwtUtil.extractEmail(token);
            Users user = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return ResponseEntity.ok(new UserResponse(user));
        } catch (Exception e) {
            logger.error("토큰 검증 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error validating token");
        }
    }
}
