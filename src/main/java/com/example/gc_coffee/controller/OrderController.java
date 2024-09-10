package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.request.OrderUpdateRequest;
import com.example.gc_coffee.dto.response.OrderItemResponse;
import com.example.gc_coffee.dto.response.OrderResponse;
import com.example.gc_coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> register(@Validated @RequestBody OrderCreateRequest orderCreateRequest) {
        OrderResponse orderResponse = orderService.register(orderCreateRequest);
        return ResponseEntity.ok(ApiResponse.success(orderResponse));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> read(@PathVariable String email) {
        return ResponseEntity.ok(ApiResponse.success(orderService.readOrders(email)));
    }

    @PutMapping("/{orderId}/items")
    public ResponseEntity<ApiResponse> updateOrderItems(
            @PathVariable Long orderId,
            @RequestBody OrderUpdateRequest orderUpdateRequest) {

        List<OrderItemResponse> updatedOrderItems = orderService.updateOrderItemQuantities(orderId, orderUpdateRequest);

        return ResponseEntity.ok(ApiResponse.success(updatedOrderItems));
    }
}