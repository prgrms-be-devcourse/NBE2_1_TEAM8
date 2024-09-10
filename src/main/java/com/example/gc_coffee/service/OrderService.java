package com.example.gc_coffee.service;

import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.request.OrderItemCreateRequest;
import com.example.gc_coffee.dto.request.OrderUpdateRequest;
import com.example.gc_coffee.dto.response.OrderItemResponse;
import com.example.gc_coffee.dto.response.OrderResponse;
import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.exception.OrderException;
import com.example.gc_coffee.exception.ProductException;
import com.example.gc_coffee.repository.OrderItemRepository;
import com.example.gc_coffee.repository.OrderRepository;
import com.example.gc_coffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderResponse register(OrderCreateRequest orderCreateRequest) {
        //주문 시간이 오후 2시 이후면 orderStatus 를 CONFIRMED 로 설정.
        LocalTime now = LocalTime.now();
        LocalTime cutoffTime = LocalTime.of(14, 0);

        OrderStatus orderStatus = now.isAfter(cutoffTime) ? OrderStatus.CONFIRMED : OrderStatus.SHIPPED;

        //Order 객체 생성
        Order order = Order.builder()
                .email(orderCreateRequest.getEmail())
                .address(orderCreateRequest.getAddress())
                .postcode(orderCreateRequest.getPostcode())
                .orderStatus(orderStatus)
                .orderItems(new ArrayList<>())
                .build();

        //OrderItem(Product, Order 정보 필요) 객체 생성 및 저장
        for (OrderItemCreateRequest orderItemCreateRequest : orderCreateRequest.getOrderItems()) {
            Product product = productRepository.findById(orderItemCreateRequest.getProductId())
                    .orElseThrow(ProductException.NOT_FOUND::get);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .category(product.getProductCategory())
                    .price(product.getPrice())
                    .quantity(orderItemCreateRequest.getQuantity())
                    .build();

            order.getOrderItems().add(orderItem);
        }

        //Order 및 OrderItem 저장
        try {
            Order saved = orderRepository.save(order);
            return new OrderResponse(saved);
        } catch (Exception e) {
            throw OrderException.CREATION_FAILED.get();
        }
    }

    public List<OrderResponse> readOrders(String email) {
        List<Order> orders = orderRepository.findAllByEmail(email);

        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }
    public List<OrderItemResponse> updateOrderItemQuantities(Long orderId, OrderUpdateRequest orderUpdateRequest) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderException.NOT_FOUND::get);

        List<OrderItemResponse> updatedOrderItems = new ArrayList<>();


        orderUpdateRequest.getItemQuantityUpdates().forEach((orderItemId, newQuantity) -> {
            OrderItem orderItem = orderItemRepository.findById(orderItemId)
                    .orElseThrow(OrderException.ITEM_NOT_FOUND::get);

            // 수량이 0 이하인 경우 삭제 처리
            if (newQuantity <= 0) {
                orderItemRepository.delete(orderItem);
            } else {

                orderItem.setQuantity(newQuantity);
                orderItemRepository.save(orderItem);
            }
            OrderItemResponse response = OrderItemResponse.builder()
                    .orderItemId(orderItem.getOrderItemId())
                    .productId(orderItem.getProduct().getId())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();

            updatedOrderItems.add(response);
        });

        return updatedOrderItems;
    }
}
