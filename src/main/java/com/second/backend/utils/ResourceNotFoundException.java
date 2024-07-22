package com.second.backend.utils;

// RuntimeException을 상속받은 커스텀 예외 클래스
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message); // 예외 메시지를 부모 클래스의 생성자에 전달
    }
}