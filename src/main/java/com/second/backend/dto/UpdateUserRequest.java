package com.second.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UpdateUserRequest {
    private String updateName;
    private String updatePhone;
    private String updateAddress;
    private String shippingInfo;
}
