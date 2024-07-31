// RegisterRequest.java
package com.second.backend.model;

public class RegisterRequest {
    private String name;
    private String phoneNum; // 변경된 부분
    private String email;
    private String password;
    private String confirmPassword;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() { // 변경된 부분
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) { // 변경된 부분
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
