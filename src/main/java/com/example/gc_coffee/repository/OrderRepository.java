package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByEmail(String email);
    List<Order> findAllByEmail(String email);
}
