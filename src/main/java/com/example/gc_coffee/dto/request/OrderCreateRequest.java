package com.example.gc_coffee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateRequest {
    private String email;
    private String address;
    private String postcode;
    private List<OrderItemCreateRequest> orderItems;
}
