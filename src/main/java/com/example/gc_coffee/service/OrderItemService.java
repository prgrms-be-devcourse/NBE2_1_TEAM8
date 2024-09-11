package com.example.gc_coffee.service;

import com.example.gc_coffee.dto.response.OrderItemResponse;
import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import com.example.gc_coffee.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItemResponse> getOrderedItems() {
        // 주문 상태가 ORDERED인 항목만 조회
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderStatus(OrderStatus.ORDERED);

        return orderItems.stream().map(orderItem -> {
            Order order = orderItem.getOrder();
            return OrderItemResponse.builder()
                    .orderItemId(orderItem.getOrderItemId())
                    .productId(orderItem.getProduct().getId())
                    .quantity(orderItem.getQuantity())
                    .orderId(order.getOrderId())
                    .email(order.getEmail())
                    .address(order.getAddress())
                    .postcode(order.getPostcode())
                    .orderStatus(order.getOrderStatus())
                    .build();
        }).collect(Collectors.toList());
    }
}