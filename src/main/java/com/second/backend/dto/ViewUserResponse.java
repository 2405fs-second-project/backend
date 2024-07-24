package com.second.backend.dto;

import com.second.backend.model.Users;
import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ViewUserResponse {

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

    public ViewUserResponse(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
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
