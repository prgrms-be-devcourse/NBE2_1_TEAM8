package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    //static Optional<Object> findByOrderItemId(Long orderItemId);

    Optional<OrderItem> findByOrder_OrderId(Long orderId);
<<<<<<< feat/#39/주문-수정-및-삭제-기능-구현
    Optional<OrderItem> findByOrderItemId(Long orderItemId);
=======
    List<OrderItem> findByOrder_OrderStatus(OrderStatus status);
>>>>>>> main
}
