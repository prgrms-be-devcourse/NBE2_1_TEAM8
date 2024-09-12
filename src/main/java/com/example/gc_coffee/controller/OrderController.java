package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.request.OrderUpdateRequest;
import com.example.gc_coffee.dto.response.OrderItemResponse;
import com.example.gc_coffee.dto.response.OrderResponse;
import com.example.gc_coffee.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 등록", description = "새로운 주문을 등록합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 등록 성공 예시")
    public ResponseEntity<ApiResponse<OrderResponse>> register(@Validated @RequestBody OrderCreateRequest orderCreateRequest) {
        OrderResponse orderResponse = orderService.register(orderCreateRequest);
        return ResponseEntity.ok(ApiResponse.success(orderResponse));
    }

    @GetMapping("/{email}")
    @Operation(summary = "주문 조회", description = "이메일로 주문 목록을 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 조회 성공 예시")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> read(@Parameter(description = "사용자의 이메일", required = true)
                                                                     @PathVariable String email)
    {
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