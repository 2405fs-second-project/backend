package com.second.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ViewOrderResponse {

    private Integer userId;
    private Integer totalPrice;
    private String orderNumber;
    private LocalDateTime orderDate;
}
