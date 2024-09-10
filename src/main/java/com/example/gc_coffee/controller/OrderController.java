package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.response.OrderResponse;
import com.example.gc_coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
}