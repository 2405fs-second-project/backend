package com.second.backend.dto;

import com.second.backend.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddUserRequest {

    private String name;
    private String phoneNum;
    private String email;
    private String password;

    public Users toEntity() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return Users.builder()
                .name(name)
                .phoneNum(phoneNum)
                .email(email)
                .password(encoder.encode(password))
                .build();
    }
}
