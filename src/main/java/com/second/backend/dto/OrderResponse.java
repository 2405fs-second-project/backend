package com.second.backend.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class OrderResponse { //주문 생성 후 응답을 위한 DTO
    private String orderNumber;
}
