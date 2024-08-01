package com.second.backend.service;

import com.second.backend.model.LoginRequest;
import com.second.backend.model.RegisterRequest;
import com.second.backend.model.Users;
import com.second.backend.model.AuthenticationResponse;
import com.second.backend.repository.UsersRepository;
import com.second.backend.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsersRepository usersRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

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
        Users newUser = Users.builder()
                .name(registerRequest.getName())
                .phoneNum(registerRequest.getPhoneNum())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

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
