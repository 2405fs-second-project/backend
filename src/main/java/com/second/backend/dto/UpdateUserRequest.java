package com.second.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateUserRequest {
    private String updateName;
    private String updatePhone;
    private String updateAddress;
    private String shippingInfo;
}
