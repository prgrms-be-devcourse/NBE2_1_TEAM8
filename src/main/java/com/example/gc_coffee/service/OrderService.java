package com.example.gc_coffee.service;

import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.request.OrderItemCreateRequest;
import com.example.gc_coffee.dto.request.OrderUpdateRequest;
import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.repository.OrderItemRepository;
import com.example.gc_coffee.repository.OrderRepository;
import com.example.gc_coffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import static java.nio.file.Files.delete;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public void register(OrderCreateRequest orderCreateRequest) {
        //주문 시간이 오후 2시 이후면 orderStatus 를 CONFIRMED 로 설정.
        LocalTime now = LocalTime.now();
        LocalTime cutoffTime = LocalTime.of(14, 0);

        OrderStatus orderStatus = now.isAfter(cutoffTime) ? OrderStatus.CONFIRMED : OrderStatus.SHIPPED;

        //email 로 Order 검색 -> 처음 주문하는 고객이면 Order 생성
        Order order = orderRepository.findByEmail(orderCreateRequest.getEmail())
                .orElseGet(() -> orderRepository.save(Order.builder()
                        .email(orderCreateRequest.getEmail())
                        .address(orderCreateRequest.getAddress())
                        .postcode(orderCreateRequest.getPostcode())
                        .orderStatus(orderStatus)
                        .orderItems(new ArrayList<>())
                        .build()));

        //OrderItem(Product, Order 정보 필요) 객체 생성 후 저장
        for (OrderItemCreateRequest orderItemCreateRequest : orderCreateRequest.getOrderItems()) {
            Product product = productRepository.findById(orderItemCreateRequest.getProductId())
                    .orElseThrow(RuntimeException::new);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .category(orderItemCreateRequest.getCategory())
                    .price(orderItemCreateRequest.getPrice())
                    .quantity(orderItemCreateRequest.getQuantity())
                    .build();

            orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);
    }

    /**
     * 주문 수정
     */
    public void updateOrderItemQuantities(OrderUpdateRequest orderUpdateRequest) {

        if (orderUpdateRequest.getItemQuantityUpdates() != null) {
            for (Map.Entry<Long, Integer> entry : orderUpdateRequest.getItemQuantityUpdates().entrySet()) {
                Long orderItemId = entry.getKey();
                Integer newQuantity = entry.getValue();

                OrderItem orderItem = orderItemRepository.findById(orderItemId)
                        .orElseThrow(() -> new RuntimeException("주문 항목을 찾을 수 없습니다."));

                // 수량이 0 이하인 경우 삭제
                if (newQuantity <= 0) {
                    orderItemRepository.delete(orderItem);
                } else {
                    // 수량 수정
                    orderItem.setQuantity(newQuantity);
                    orderItemRepository.save(orderItem);
                }
            }

        }
    }
}