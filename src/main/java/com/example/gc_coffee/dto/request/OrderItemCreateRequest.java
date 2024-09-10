package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemCreateRequest {
    @NotNull(message = "상품 ID는 비어있을 수 없습니다.")
    private Long productId;

    @NotNull(message = "카테고리는 비어있을 수 없습니다.")
    private ProductCategory category;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private int price;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;
}
