package com.second.backend.model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String phoneNum;
    private String email;
    private String password;
    private String confirmPassword;
}
