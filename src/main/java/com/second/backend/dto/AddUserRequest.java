package com.second.backend.dto;

import com.second.backend.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddUserRequest {

    private String name;
    private String password;
    private String email;
    private String phone_num;
    private String address;
    private String profile_picture_url;
    private Users.Gender gender;
    private String about_me;
    private String update_name;
    private String update_address;
    private String update_phone;
    private String shipping_info;


    public Users toEntity() {
        return Users.builder()
                .name(name)
                .email(email)
                .password(password)
                .phone_num(phone_num)
                .address(address)
                .gender(gender)
                .profile_picture_url(profile_picture_url)
                .about_me(about_me)
                .update_name(update_name)
                .update_address(update_address)
                .update_phone(update_phone)
                .shipping_info(shipping_info)
                .build();
    }

}
