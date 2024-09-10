package com.example.gc_coffee.dto.response;

import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {

    private Long orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private int totalOrderAmount;
    private List<OrderItemResponse> orderItems;

    // 생성자
    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();
        this.orderStatus = order.getOrderStatus();
        this.createdAt = order.getCreatedAt();
        this.totalOrderAmount = calculateTotalOrderAmount(order);
        this.orderItems = convertOrderItemsToResponse(order);
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
