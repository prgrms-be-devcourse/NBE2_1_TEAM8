package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    //static Optional<Object> findByOrderItemId(Long orderItemId);

    Optional<OrderItem> findByOrder_OrderId(Long orderId);
    Optional<OrderItem> findByOrderItemId(Long orderItemId);
}
