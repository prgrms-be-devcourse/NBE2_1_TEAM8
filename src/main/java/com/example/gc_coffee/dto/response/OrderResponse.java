package com.example.gc_coffee.dto.response;

import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {

    @Schema(description = "주문 ID", example = "1")
    private Long orderId;

    @Schema(description = "주문자 이메일", example = "example@example.com")
    private String email;

    @Schema(description = "배송 주소", example = "서울특별시 강남구 역삼동")
    private String address;

    @Schema(description = "우편번호", example = "06130")
    private String postcode;

    @Schema(description = "주문 상태", example = "CONFIRMED")
    private OrderStatus orderStatus;

    @Schema(description = "주문 생성 시간")
    private LocalDateTime createdAt;

    @Schema(description = "총 주문 금액", example = "50000")
    private int totalOrderAmount;

    @Schema(description = "주문 항목 리스트")
    private List<OrderItemResponse> orderItems;

    // 생성자
    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();
        this.orderStatus = order.getOrderStatus();
        this.createdAt = order.getCreatedAt();
        this.totalOrderAmount = calculateTotalOrderAmount(order); //장바구니 총금액
        this.orderItems = convertOrderItemsToResponse(order);//항목별 총금액
    }

    // 전체 주문 금액 계산 메서드
    private int calculateTotalOrderAmount(Order order) {
        int totalAmount = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            totalAmount += orderItem.getPrice() * orderItem.getQuantity();  // 각 항목의 총 금액 계산
        }
        return totalAmount;
    }

    // OrderItem을 OrderItemResponse로 변환
    private List<OrderItemResponse> convertOrderItemsToResponse(Order order) {
        List<OrderItemResponse> responseList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            responseList.add(mapToOrderItemResponse(orderItem));
        }
        return responseList;
    }
    // OrderItem을 OrderItemResponse로 변환하여 응답 생성
    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
        int totalPrice = orderItem.getPrice() * orderItem.getQuantity();  // 항목별 총 금액 계산

        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .totalPrice(totalPrice)
                .build();
    }
}
