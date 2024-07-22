package com.github.userpage.web.dto;

import com.github.userpage.model.Users;
import lombok.Getter;

@Getter
public class UserResponse {

    private String name;
    private String password;
    private String email;
    private String phone_num;
    private String address;
    private String profile_picture_url;
    private Users.Gender gender;
    private String about_me;
    private String update_name;
    private String update_phone;
    private String update_address;
    private String shipping_info;

    public UserResponse(Users user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone_num = user.getPhone_num();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.profile_picture_url = user.getProfile_picture_url();
        this.about_me = user.getAbout_me();
        this.update_name = user.getUpdate_name();
        this.update_phone = user.getUpdate_phone();
        this.update_address = user.getUpdate_address();
        this.shipping_info = user.getShipping_info();
    }
}
