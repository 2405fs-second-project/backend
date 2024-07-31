// AuthService.java
package com.second.backend.service;

import com.second.backend.model.LoginRequest;
import com.second.backend.model.RegisterRequest;
import com.second.backend.model.Users;
import com.second.backend.model.AuthenticationResponse;
import com.second.backend.repository.UsersRepository;
import com.second.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        // 비밀번호 확인
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // 이메일 중복 체크
        if (usersRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already taken");
        }

        // 사용자 정보 저장
        Users newUser = new Users();
        newUser.setName(registerRequest.getName());
        newUser.setPhoneNum(registerRequest.getPhoneNum()); // 전화번호 필드 수정
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // 비밀번호 암호화

        usersRepository.save(newUser);

        // 가입 성공 응답
        String jwt = jwtUtil.createToken(newUser.getEmail(), newUser.getAuthorities().toString());
        return new AuthenticationResponse(jwt, newUser.getId(), newUser.getName());
    }

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        Users user = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String jwt = jwtUtil.createToken(user.getEmail(), user.getAuthorities().toString());

        return new AuthenticationResponse(jwt, user.getId(), user.getName());
    }
}
