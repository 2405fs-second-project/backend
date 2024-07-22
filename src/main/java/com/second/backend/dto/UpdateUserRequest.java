package com.second.backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateUserRequest {
    private String update_name;
    private String update_phone;
    private String update_address;
    private String shipping_info;
}
