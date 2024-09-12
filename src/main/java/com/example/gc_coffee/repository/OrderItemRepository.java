package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByOrder_OrderId(Long orderId);
    List<OrderItem> findByOrder_OrderStatusIn(List<OrderStatus> list);
}
