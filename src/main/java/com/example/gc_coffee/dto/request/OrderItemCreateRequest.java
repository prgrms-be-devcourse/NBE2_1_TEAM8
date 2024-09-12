package com.example.gc_coffee.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemCreateRequest {
    @NotNull(message = "상품 ID는 비어있을 수 없습니다.")
    @Schema(description = "상품 ID", example = "3", requiredMode = RequiredMode.REQUIRED)
    private Long productId;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    @Schema(description = "수량", example = "2", requiredMode = RequiredMode.REQUIRED)
    private int quantity;
}
