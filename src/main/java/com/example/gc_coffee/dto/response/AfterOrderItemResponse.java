package com.example.gc_coffee.dto.response;

import com.example.gc_coffee.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@Schema(description = "주문 후 항목 조회 객체")
public class AfterOrderItemResponse {

    @Schema(description = "주문 항목 ID", example = "1")
    private Long orderItemId;

    @Schema(description = "상품 ID", example = "101")
    private Long productId;

    @Schema(description = "주문된 상품 수량", example = "2")
    private int quantity;

    @Schema(description = "주문 ID", example = "1001")
    private Long orderId;

    @Schema(description = "상품 이름", example = "아메리카노")
    private String productName;

    @Schema(description = "주문자 이메일", example = "user@example.com")
    private String email;

    @Schema(description = "배송 주소", example = "서울특별시 영등포구 당산동")
    private String address;

    @Schema(description = "우편번호", example = "06110")
    private String postcode;

    @Schema(description = "주문 상태", example = "ORDERED")
    private OrderStatus orderStatus;
}