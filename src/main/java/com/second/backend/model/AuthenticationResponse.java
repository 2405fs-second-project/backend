package com.second.backend.model;

public class AuthenticationResponse {
    private String jwt;
    private Integer userId;
    private String userName;

    public AuthenticationResponse(String jwt, Integer userId, String userName) {
        this.jwt = jwt;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters and Setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
