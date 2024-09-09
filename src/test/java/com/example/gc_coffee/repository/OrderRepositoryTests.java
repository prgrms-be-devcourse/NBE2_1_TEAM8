package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        Order order = Order.builder()
                .email("test@example.com")
                .address("123 Test")
                .postcode("12345")
                .orderStatus(OrderStatus.ORDERED)
                .build();
        orderRepository.save(order);
    }

    @Test
    public void testSave() {
        Order newOrder = Order.builder()
                .email("new@example.com")
                .address("123 New")
                .postcode("123456")
                .orderStatus(OrderStatus.ORDERED)
                .build();

        Order savedOrder = orderRepository.save(newOrder);
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    public void testDelete() {
        String email = "test@example.com";

        assertTrue(orderRepository.findByEmail(email).isPresent());
        orderRepository.deleteById(orderRepository.findByEmail(email).get().getOrderId());

        assertFalse(orderRepository.findByEmail(email).isPresent());
    }

    @Test
    @Rollback(value = false)
    public void testFindByEmail() {
        Optional<Order> foundOrder = orderRepository.findByEmail("test@example.com");
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getEmail()).isEqualTo("test@example.com");
    }
}
