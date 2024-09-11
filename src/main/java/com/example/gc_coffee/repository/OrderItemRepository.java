package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrder_OrderId(Long orderId);
    List<OrderItem> findByOrder_OrderStatus(OrderStatus status);  // 주문 상태에 따른 주문 항목 필터링

}