package com.second.backend.dto;

import com.second.backend.model.Users;
import lombok.Getter;

@Getter
public class UserResponse {

    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phoneNum;
    private String address;
    private String profilePictureUrl;
    private Users.Gender gender;
    private String aboutMe;
    private String updateName;
    private String updatePhone;
    private String updateAddress;
    private String shippingInfo;

    public UserResponse(Users user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNum = user.getPhone_num();
        this.address = user.getAddress();
        this.profilePictureUrl = user.getProfile_picture_url();
        this.gender = user.getGender();
        this.aboutMe = user.getAbout_me();
        this.updateName = user.getUpdate_name();
        this.updatePhone = user.getUpdate_phone();
        this.updateAddress = user.getUpdate_address();
        this.shippingInfo = user.getShipping_info();
    }
}
