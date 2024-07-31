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
public class ViewUserResponse {

    private Integer id;
    private String name;
    private String email;
    private String phoneNum; // 수정된 부분
    private String address;
    private String profilePictureUrl;
    private String aboutMe;
    private String updateName;
    private String updatePhone;
    private String updateAddress;
    private String shippingInfo;

    public ViewUserResponse(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNum = user.getPhoneNum(); // 수정된 부분
        this.address = user.getAddress();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.aboutMe = user.getAboutMe();
        this.updateName = user.getUpdateName();
        this.updatePhone = user.getUpdatePhone();
        this.updateAddress = user.getUpdateAddress();
        this.shippingInfo = user.getShippingInfo();
    }
}
