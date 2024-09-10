package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrder_OrderId(Long orderId);
}
