package com.github.userpage.web.controller;

import com.github.userpage.model.Users;
import com.github.userpage.service.UsersService;
import com.github.userpage.web.dto.UpdateUserRequest;
import com.github.userpage.web.dto.ViewUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users") // Base URL을 추가
public class UserViewController {

    private final UsersService userService;

    @GetMapping("/{id}")
    @ResponseBody // JSON 응답을 반환
    public ResponseEntity<ViewUserResponse> getUserApi(@PathVariable Integer id) {
        ViewUserResponse user = new ViewUserResponse(userService.findById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Users> uploadProfilePicture(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        String base64Image = request.get("image");

        Users user;
        try {
            user = userService.findById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        user.setProfile_picture_url(base64Image);

        try {
            user = userService.save(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/shipping")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        Users user;
        try {
            user = userService.findById(id);
            user.setUpdate_name(request.getUpdate_name());
            user.setUpdate_address(request.getUpdate_address());
            user.setUpdate_phone(request.getUpdate_phone());
            user.setShipping_info(request.getShipping_info());
            user = userService.save(user);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

