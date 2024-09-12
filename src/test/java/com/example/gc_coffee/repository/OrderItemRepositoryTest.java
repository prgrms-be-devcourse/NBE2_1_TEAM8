package com.example.gc_coffee.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.gc_coffee.entity.Order;
import com.example.gc_coffee.entity.OrderItem;
import com.example.gc_coffee.entity.OrderStatus;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.entity.ProductCategory;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        Product product1 = Product.builder()
                .productName("Columbia Coffee")
                .productCategory(ProductCategory.COFFEE_BEAN_PACKAGE)
                .price(10000)
                .description("콜롬비아의 맛있는 커피")
                .build();
        productRepository.save(product1);

        Order order1 = Order.builder()
                .email("test1@example.com")
                .address("123 Test")
                .postcode("123")
                .orderStatus(OrderStatus.ORDERED)
                .build();
        orderRepository.save(order1);

        OrderItem orderItem1 = OrderItem.builder()
                .product(product1)
                .order(order1)
                .category(product1.getProductCategory())
                .price(product1.getPrice())
                .quantity(1)
                .build();
        orderItemRepository.save(orderItem1);

    }

    @Test
    public void testFindByOrderStatus() {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderStatusIn(
                Arrays.asList(OrderStatus.ORDERED, OrderStatus.CONFIRMED, OrderStatus.SHIPPED, OrderStatus.DELIVERED));

        assertThat(orderItems).isNotEmpty();

        orderItems.forEach(orderItem -> {
            Order order = orderItem.getOrder();
            System.out.println("주문번호: " + order.getOrderId());
            System.out.println("이메일: " + order.getEmail());
            System.out.println("주소: " + order.getAddress());
            System.out.println("우편번호: " + order.getPostcode());
            System.out.println("배송 상태: " + order.getOrderStatus());

            assertThat(order.getOrderStatus()).isIn(OrderStatus.ORDERED, OrderStatus.CONFIRMED);
            assertThat(order.getEmail()).isNotNull();
            assertThat(order.getAddress()).isNotNull();
            assertThat(order.getPostcode()).isNotNull();
        });
    }

    @Test
    @Transactional
    @Commit
    public void testUpdateTransactional() {
        Long oid = 42L;
        Optional<OrderItem> foundOrderItem = orderItemRepository.findByOrder_OrderId(oid);
        Optional<Order> foundOrder = orderRepository.findById(oid);

        foundOrderItem.get().changeQuantity(2);
        foundOrder.get().changeAddress("change address");

        assertEquals(2, foundOrderItem.get().getQuantity());
        assertEquals("change address", foundOrder.get().getAddress());
    }

    @Test
    public void testDelete() {
        Long oid = 42L;
        Optional<OrderItem> foundOrderItem = orderItemRepository.findByOrder_OrderId(oid);

        assertTrue(orderItemRepository.findByOrder_OrderId(foundOrderItem.get().getOrderItemId()).isPresent());
        orderItemRepository.deleteById(foundOrderItem.get().getOrderItemId());
        orderRepository.deleteById(oid);
    }
}
