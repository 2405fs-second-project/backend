package com.second.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserOrderInfoResponse {
    private String name;
    private String phoneNum;
    private String updateName;
    private String updatePhoneNum;
    private String updateAddress;
    private String shippingInfo;
}
