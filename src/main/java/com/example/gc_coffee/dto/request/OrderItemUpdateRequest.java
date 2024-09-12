package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.entity.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.gc_coffee.entity.QProduct.product;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "주문 목록 수정 시 수정할 내용 지정")
public class OrderItemUpdateRequest {
    //주문관리 id
    @Schema(description = "주문 목록 ID", example = "1")
    private Long orderItemId;

    //주문 id
    @NotNull(message = "주문은 비어있을 수 없습니다.")
    @Schema(description = "주문 ID", example = "1")
    private Order order;

//    //상품 id
//    @NotNull(message = "상품은 비어있을 수 없습니다.")
//    @Schema(description = "상품 ID", example = "1")
//    private Product product;
//
//    // 제품 카테고리
//    @NotNull(message = "상품 유형은 기재는 필수입니다.")
//    @Schema(description = "상품 카테고리", example = "COFFEE_BEAN_PACKAGE")
//    private ProductCategory productCategory;
//
//    // 가격
//    @NotNull(message = "가격은 필수 기재 사항입니다.")
//    @Schema(description = "가격", example = "10000")
//    private Integer price;

    //수량
    @Schema(description = "수량", example = "3")
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private Integer quantity;

    // OrderItem 객체로부터 업데이트 요청 생성자
    public OrderItemUpdateRequest(OrderItem orderItem) {
        this.orderItemId = orderItem.getOrderItemId();
        this.order = orderItem.getOrder();
//        this.product = orderItem.getProduct();
//        this.productCategory = orderItem.getCategory();
//        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }

    // OrderItem 엔티티로 변환하는 메서드
    public OrderItem toEntity() {
        return OrderItem.builder()
                //.category(productCategory)
                .order(order)
                //.price(price)
                //.product(product)
                .quantity(quantity)
                .build();
    }
    public Long getOrderId() {
        if (order == null || order.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID is missing");
        }
        return order.getOrderId();
    }
}