package com.second.backend.controller;

import com.second.backend.dto.UpdateUserRequest;
import com.second.backend.dto.ViewUserResponse;
import com.second.backend.model.Users;
import com.second.backend.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserViewController {

    private final UsersService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ViewUserResponse> getUserApi(@PathVariable Integer id) {
        ViewUserResponse user = new ViewUserResponse(userService.findById(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Users> uploadProfilePicture(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Users user = userService.uploadProfilePicture(id, file);
            return ResponseEntity.ok(user);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/shipping")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        Users user;
        try {
            user = userService.updateUser(
                    id,
                    request.getUpdateName(),
                    request.getUpdateAddress(),
                    request.getUpdatePhone(),
                    request.getShippingInfo()
            );
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
