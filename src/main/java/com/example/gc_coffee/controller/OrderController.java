package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.request.OrderUpdateRequest;
import com.example.gc_coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody OrderCreateRequest orderCreateRequest) {
        orderService.register(orderCreateRequest);
        return ResponseEntity.ok(ApiResponse.success(null));

    }

    @PutMapping("/{orderId}/items")
    public ResponseEntity<ApiResponse> updateOrderItems(
            @PathVariable Long orderId,
            @RequestBody OrderUpdateRequest orderUpdateRequest) {

        orderService.updateOrderItemQuantities(orderUpdateRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}