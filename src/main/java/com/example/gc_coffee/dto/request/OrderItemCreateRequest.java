package com.example.gc_coffee.dto.request;

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

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;
}
