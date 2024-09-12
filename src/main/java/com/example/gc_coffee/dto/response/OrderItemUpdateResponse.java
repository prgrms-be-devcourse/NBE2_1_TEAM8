package com.example.gc_coffee.dto.response;

import com.example.gc_coffee.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderItemUpdateResponse {

    private Long OrderItemId;
    private Long OrderId;
    private Integer Quantity;
    private String Address;
    private String Postcode;

    public static OrderItemUpdateResponse fromOrderItem(OrderItem orderItem) {
        return new OrderItemUpdateResponse(
                orderItem.getOrderItemId(),
                orderItem.getOrder().getOrderId(),
                orderItem.getQuantity(),
                orderItem.getOrder().getAddress(),
                orderItem.getOrder().getPostcode()
        );
    }


}
