package com.second.backend.dto;

import com.second.backend.model.Users;
import lombok.Getter;

@Getter
public class UserResponse {

    private Integer id;
    private String name;
    private String email;
    private String phoneNum;
    private String address;
    private String profilePictureUrl;
    private Users.Gender gender;
    private String aboutMe;

    public UserResponse(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNum = user.getPhone_num();
        this.address = user.getAddress();
        this.profilePictureUrl = user.getProfile_picture_url();
        this.gender = user.getGender();
        this.aboutMe = user.getAbout_me();
    }
}
