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

    private final UsersRepository usersRepository;
    private final String uploadDir = "src/main/resources/img/profile"; // 변경된 파일 저장 경로

    @Transactional
    public Users save(Users user) {
        return usersRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Users findById(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found with id : " + id));
    }

    @Transactional
    public Users uploadProfilePicture(Integer id, MultipartFile file) throws IOException {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 파일 타입 검증
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("잘못된 파일 타입입니다. 이미지 파일만 허용됩니다.");
        }

        String fileUrl = storeFile(file);
        user.setProfilePictureUrl(fileUrl);

        return usersRepository.save(user);
    }

    @Transactional
    public Users updateUser(Integer id, String updateName, String updateAddress, String updatePhone, String shippingInfo) {
        return usersRepository.findById(id)
                .map(user -> {
                    user.setUpdateName(updateName != null ? updateName : user.getUpdateName());
                    user.setUpdateAddress(updateAddress != null ? updateAddress : user.getUpdateAddress());
                    user.setUpdatePhone(updatePhone != null ? updatePhone : user.getUpdatePhone());
                    user.setShippingInfo(shippingInfo != null ? shippingInfo : user.getShippingInfo());
                    return usersRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public String storeFile(MultipartFile file) throws IOException {
        // 파일 저장 경로 설정
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 이름 및 저장 경로 설정
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new IOException("파일 이름이 없습니다.");
        }
        String uniqueFilename = UUID.randomUUID().toString() + "-" + filename;
        Path filePath = uploadPath.resolve(uniqueFilename);

        // 파일 저장
        try {
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new IOException("파일 저장 중 오류가 발생했습니다.", e);
        }

        // 저장된 파일의 URL 반환
        return "/img/profile/" + uniqueFilename; // 변경된 URL 경로
    }
}
