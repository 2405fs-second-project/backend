package com.second.backend.dto;

import com.second.backend.model.Orders;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewOrderResponse {

    private Integer id;
    private Integer userId;
    private Integer total_price;
    private String order_number;
    private List<ViewOrderItemsResponse> orderItems;


    public ViewOrderResponse(Orders order) {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.total_price = order.getTotal_price();
        this.order_number = order.getOrder_number();
        this.orderItems = new ArrayList<>();
    }
}
