package com.example.gc_coffee.dto.response;

import com.example.gc_coffee.entity.OrderStatus;  // OrderStatus 클래스 import
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemResponse {
    private Long orderItemId;
    private Long productId;
    private int quantity;
    private int price;
    private int totalPrice;

    private Long orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
}

