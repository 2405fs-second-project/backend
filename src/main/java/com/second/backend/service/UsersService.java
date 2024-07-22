package com.second.backend.service;


import com.second.backend.model.Users;
import com.second.backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository userRepository;

    public Users save(Users user) {
        return userRepository.save(user);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found with id : " + id));
    }

    public Users findByName(String name) {
        return userRepository.findByName(name);
    }

    public Users uploadProfilePicture(Integer id, String base64Image) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfile_picture_url(base64Image);
        return userRepository.save(user);
    }
}


