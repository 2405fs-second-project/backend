package com.second.backend.service;

import com.second.backend.model.Users;
import com.second.backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.*;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository userRepository;
    private final String uploadDir = "/System/Volumes/Data/path/to/uploadDir";// 파일 저장 경로


    public Users save(Users user) { //쓰기 작업이 포홤되어 있어서 @Transactional 기능 포함
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Users findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found with id : " + id));
    }


    @Transactional
    public Users uploadProfilePicture(Integer id, MultipartFile file) throws IOException {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 파일 타입 검증
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("잘못된 파일 타입입니다. 이미지 파일만 허용됩니다.");
        }

        String fileUrl = storeFile(file);
        user.setProfilePictureUrl(fileUrl);

        return userRepository.save(user);
    }

    @Transactional
    public Users updateUser(Integer id, String updateName, String updateAddress, String updatePhone, String shippingInfo) {
        return userRepository.findById(id)
                .map(user -> {
                    Users updatedUser = Users.builder()
                            .id(user.getId())
                            .updateName(updateName != null ? updateName : user.getUpdateName())
                            .updateAddress(updateAddress != null ? updateAddress : user.getUpdateAddress())
                            .updatePhone(updatePhone != null ? updatePhone : user.getUpdatePhone())
                            .shippingInfo(shippingInfo != null ? shippingInfo : user.getShippingInfo())
                            .profilePictureUrl(user.getProfilePictureUrl())
                            .build();
                    return userRepository.save(updatedUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public String storeFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new IOException("Filename is missing");
        }

        // 파일 이름을 UUID로 변경하여 중복 문제 방지
        String uniqueFilename = UUID.randomUUID().toString() + "-" + filename;
        Path filePath = uploadPath.resolve(uniqueFilename);
        file.transferTo(filePath.toFile());

        // 파일 URL을 반환
        return "/img/" + uniqueFilename;
    }

}


