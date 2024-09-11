package com.example.gc_coffee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemModificationRequest {
    private OrderItemUpdateRequest orderItemUpdateRequest;
    private OrderCreateRequest orderCreateRequest;
}
