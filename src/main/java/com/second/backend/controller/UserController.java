//package com.second.backend.controller;
//
////import com.second.backend.model.User;
////import com.second.backend.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3002")
//public class UserController {
//
//
//
//        @Autowired
//        private UserService userService;
//
//        @PostMapping("/register")
//        public ResponseEntity<String> registerUser(@RequestBody User user) {
//            try {
//                userService.registerUser(user);
//                return ResponseEntity.ok("User registered successfully");
//            } catch (Exception e) {
//                e.printStackTrace(); // 예외를 콘솔에 기록
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
//            }
//        }
//
//        @PostMapping("/login")
//        public ResponseEntity<String> loginUser(@RequestBody User user) {
//            try {
//                if (userService.loginUser(user)) {
//                    return ResponseEntity.ok("Login successful");
//                } else {
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//                }
//            } catch (Exception e) {
//                e.printStackTrace(); // 예외를 콘솔에 기록
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging in");
//
//            }
//        }
//
//
//}
