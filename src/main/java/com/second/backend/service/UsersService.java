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

    public Users uploadProfilePicture(Integer id, String base64Image) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfile_picture_url(base64Image);
        return userRepository.save(user);
    }

    public Users updateUser(Integer id, String updateName, String updateAddress, String updatePhone, String shippingInfo) {
        return userRepository.findById(id)
                .map(user -> {
                    Users updatedUser = Users.builder()
                            .id(user.getId())
                            .update_name(updateName != null ? updateName : user.getUpdate_name())
                            .update_address(updateAddress != null ? updateAddress : user.getUpdate_address())
                            .update_phone(updatePhone != null ? updatePhone : user.getUpdate_phone())
                            .shipping_info(shippingInfo != null ? shippingInfo : user.getShipping_info())
                            .profile_picture_url(user.getProfile_picture_url())
                            .build();
                    return userRepository.save(updatedUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
}


