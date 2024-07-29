package com.second.backend.dto;

import com.second.backend.model.Orders;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewOrderResponse {

    private Integer userId;
    private Integer totalPrice;
    private String orderNumber;
    private LocalDateTime orderDate;

}
