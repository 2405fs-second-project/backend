package com.second.backend.dto;

import com.second.backend.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
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


}
