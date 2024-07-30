package com.second.backend.service;

import com.second.backend.model.Users;
import com.second.backend.model.LoginRequest;
import com.second.backend.model.AuthenticationResponse;
import com.second.backend.repository.UsersRepository;
import com.second.backend.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        try {
            Users user = usersRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password."));
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new UsernameNotFoundException("Invalid email or password.");
            }
            String jwt = jwtTokenProvider.createToken(user.getEmail(), null); // 역할 정보 없이 토큰 생성
            return new AuthenticationResponse(jwt, user.getId());
        } catch (Exception e) {
            logger.error("인증 중 오류", e);
            throw new RuntimeException("Authentication failed", e);
        }
    }
}
