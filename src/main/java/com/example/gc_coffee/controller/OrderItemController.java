package com.example.gc_coffee.controller;

import com.example.gc_coffee.dto.response.OrderItemResponse;
import com.example.gc_coffee.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getOrderItemsByEmail(@RequestParam String email) {
        List<OrderItemResponse> orderItems = orderItemService.getOrderedItemsByEmail(email);
        if (orderItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderItems);
    }
}