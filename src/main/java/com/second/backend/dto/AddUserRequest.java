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
    private String phoneNum;
    private String address;
    private String profilePictureUrl;
    private Users.Gender gender;
    private String aboutMe;
    private String updateName;
    private String updateAddress;
    private String updatePhone;
    private String shippingInfo;


    public Users toEntity() {
        return Users.builder()
                .name(name)
                .email(email)
                .password(password)
                .phone_num(phoneNum)
                .address(address)
                .gender(gender)
                .profile_picture_url(profilePictureUrl)
                .about_me(aboutMe)
                .update_name(updateName)
                .update_address(updateAddress)
                .update_phone(updatePhone)
                .shipping_info(shippingInfo)
                .build();
    }

}
