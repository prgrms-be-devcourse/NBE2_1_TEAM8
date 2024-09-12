package com.example.gc_coffee.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "주문 항목 조회 객체")
public class OrderItemResponse {
    @Schema(description = "주문 항목 ID", example = "1")
    private Long orderItemId;
    @Schema(description = "상품 ID", example = "101")
    private Long productId;
    @Schema(description = "주문된 상품 수량", example = "2")
    private int quantity;
    @Schema(description = "상품 가격", example = "5000")
    private int price;
    @Schema(description = "총 주문 금액", example = "10000")
    private int totalPrice;
}