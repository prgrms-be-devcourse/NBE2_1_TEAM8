package com.example.gc_coffee.service;

import com.example.gc_coffee.dto.request.OrderItemModificationRequest;
import com.example.gc_coffee.dto.response.AfterOrderItemResponse;
import com.example.gc_coffee.dto.response.OrderItemUpdateResponse;
import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.exception.OrderException;
import com.example.gc_coffee.exception.OrderItemException;
import com.example.gc_coffee.exception.ProductException;
import com.example.gc_coffee.repository.OrderItemRepository;
import com.example.gc_coffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class OrderItemService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public List<AfterOrderItemResponse> getOrderedItemsByEmail(String email) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderStatusIn(Arrays.asList(OrderStatus.ORDERED, OrderStatus.CONFIRMED, OrderStatus.SHIPPED, OrderStatus.DELIVERED));

        return orderItems.stream()
                .filter(orderItem -> orderItem.getOrder().getEmail().equals(email))
                .map(orderItem -> {
                    Order order = orderItem.getOrder();
                    Product product = orderItem.getProduct();
                    return AfterOrderItemResponse.builder()
                            .orderItemId(orderItem.getOrderItemId())
                            .productId(orderItem.getProduct().getId())
                            .quantity(orderItem.getQuantity())
                            .orderId(order.getOrderId())
                            .productName(product.getProductName())
                            .email(order.getEmail())
                            .address(order.getAddress())
                            .postcode(order.getPostcode())
                            .orderStatus(order.getOrderStatus())
                            .build();
                }).collect(Collectors.toList());
    }
  
    @Transactional
    public OrderItemUpdateResponse modify(OrderItemModificationRequest request) {
        // 수정하려는 주문 목록을 데이터베이스에서 조회
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemUpdateRequest().getOrderItemId())
                .orElseThrow(() -> OrderItemException.NOT_FOUND.get());
        Order order = orderRepository.findById(request.getOrderCreateRequest().getOrderId())
                .orElseThrow(() -> OrderException.NOT_FOUND.get());

        try {
            // 주문 수량 수정
            if (request.getOrderItemUpdateRequest().getQuantity() != null) {
                orderItem.changeQuantity(request.getOrderItemUpdateRequest().getQuantity());
            }
            // 주문 주소 수정
            if (request.getOrderCreateRequest().getAddress() != null) {
                order.changeAddress(request.getOrderCreateRequest().getAddress());
            }
            //  주문 우편번호 수정
            if (request.getOrderCreateRequest().getPostcode() != null) {
                order.changePostcode(request.getOrderCreateRequest().getPostcode());
            }

            // 변경된 상품을 저장 (자동으로 변경 감지 후 DB에 반영)
            orderItemRepository.save(orderItem);
            orderRepository.save(order);

            // 수정된 내용을 다시 반환
            return OrderItemUpdateResponse.fromOrderItem(orderItem);

        } catch (Exception e) {
            log.error("Error modifying product: " + e.getMessage());
            throw OrderItemException.NOT_MODIFIED.get();
        }
    }

    @Transactional
    public void remove(Long id) {
        // 삭제하려는 주문을 데이터베이스에서 조회
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);
        log.info("Product found: {}", optionalOrderItem.isPresent());

        OrderItem orderItem = optionalOrderItem.orElseThrow(() -> ProductException.NOT_FOUND.get());

        try {
            // 주문 삭제
            orderItemRepository.delete(orderItem);
        } catch (Exception e) {
            log.error("Error removing product: " + e.getMessage());
            throw OrderItemException.NOT_REMOVED.get();
        }

    }


}


