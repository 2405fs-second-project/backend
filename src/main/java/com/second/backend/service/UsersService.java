package com.second.backend.service;

import com.second.backend.model.Users;
import com.second.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users findById(Integer id) {
        return usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public Users uploadProfilePicture(Integer id, MultipartFile file) throws IOException {
        Users user = findById(id);
        String fileUrl = Base64.getEncoder().encodeToString(file.getBytes());
        user.setProfilePictureUrl(fileUrl);
        return usersRepository.save(user);
    }

    public Users updateUser(Integer id, String updateName, String updateAddress, String updatePhone, String shippingInfo) {
        Users user = findById(id);
        user.setUpdateName(updateName != null ? updateName : user.getUpdateName());
        user.setUpdateAddress(updateAddress != null ? updateAddress : user.getUpdateAddress());
        user.setUpdatePhone(updatePhone != null ? updatePhone : user.getUpdatePhone());
        user.setShippingInfo(shippingInfo != null ? shippingInfo : user.getShippingInfo());
        return usersRepository.save(user);
    }

    public void updateProfilePictureUrl(Integer userId, String fileUrl) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setProfilePictureUrl(fileUrl);
            usersRepository.save(user);
        }
    }

    public void updateProfilePictureBase64(Integer userId, String base64Image) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setProfilePictureUrl(base64Image);
            usersRepository.save(user);
        }
    }

    public void updateUserInfo(Integer userId, String updateName, String updateAddress, String updatePhone, String shippingInfo) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setUpdateName(updateName != null ? updateName : user.getUpdateName());
            user.setUpdateAddress(updateAddress != null ? updateAddress : user.getUpdateAddress());
            user.setUpdatePhone(updatePhone != null ? updatePhone : user.getUpdatePhone());
            user.setShippingInfo(shippingInfo != null ? shippingInfo : user.getShippingInfo());
            usersRepository.save(user);
        }
    }
}
