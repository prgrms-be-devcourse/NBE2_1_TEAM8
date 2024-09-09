package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemCreateRequest {
    private Long productId;
    private ProductCategory category;
    private int price;
    private int quantity;
}
