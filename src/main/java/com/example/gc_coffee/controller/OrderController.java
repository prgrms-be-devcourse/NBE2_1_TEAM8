package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.response.OrderResponse;
import com.example.gc_coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
