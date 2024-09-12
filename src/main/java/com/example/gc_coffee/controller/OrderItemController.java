package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.OrderItemModificationRequest;
import com.example.gc_coffee.dto.request.OrderItemUpdateRequest;
import com.example.gc_coffee.exception.OrderException;
import com.example.gc_coffee.exception.OrderItemException;
import com.example.gc_coffee.exception.OrderItemTaskException;
import com.example.gc_coffee.exception.OrderTaskException;
import com.example.gc_coffee.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.gc_coffee.dto.response.AfterOrderItemResponse;

import java.util.List;
@Tag(name="OrderItem", description = "주문 후 관리 API")
@RestController
@RequestMapping("/api/v1/orderitem")
@Tag(name = "OrderItem", description = "주문 후 관리 API")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Operation(summary = "주문 후 목록 조회", description = "주문이 완료된 목록을 조회합니다.")
    @GetMapping("/{email}")
    public ResponseEntity<List<AfterOrderItemResponse>> getOrderItemsByEmail(
            @Parameter(description = "주문자의 이메일 주소", example = "user@example.com") @PathVariable String email) {
        List<AfterOrderItemResponse> orderItems = orderItemService.getOrderedItemsByEmail(email);
        return orderItems.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(orderItems);
    }

    //주문 목록 수정
    @PutMapping("/{id}")
    @Operation(summary = "주문 목록 수정", description = "주문 목록 하나의 수량, 주소, 우편번호를 수정합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류")
    public ResponseEntity<ApiResponse> modify(
            @Parameter(required = true,description = "주문 목록 고유 번호")
            @PathVariable("id") Long id,
            @RequestBody @Valid OrderItemModificationRequest request
            ) {
        // 수정 요청의 ID와 PathVariable ID가 일치하는지 확인
        if (!id.equals(request.getOrderItemUpdateRequest().getOrderItemId())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("주문목록 ID가 일치하지 않습니다."));
        }
        // Order와 Order ID null 체크 추가
        OrderItemUpdateRequest updateRequest = request.getOrderItemUpdateRequest();
        if (updateRequest.getOrder() == null || updateRequest.getOrder().getOrderId() == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("주문 ID가 비어 있습니다."));
        }

        Long orderId = updateRequest.getOrder().getOrderId();
        if (!orderId.equals(request.getOrderCreateRequest().getOrderId())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("주문 ID가 일치하지 않습니다."));
        }

        try {
            OrderItemUpdateRequest updatedOrderItem = orderItemService.modify(request);
            return ResponseEntity.ok(ApiResponse.success(updatedOrderItem));
        } catch (OrderItemTaskException e) {
            if (e.getMessage().equals(OrderItemException.NOT_FOUND.get().getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("주문목록을 찾을 수 없습니다."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("주문목록 수정 중 오류가 발생했습니다."));
        } catch (OrderTaskException e) {
            if (e.getMessage().equals(OrderException.NOT_FOUND.get().getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("주문을 찾을 수 없습니다."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("주문 수정 중 오류가 발생했습니다."));
        }
    }

    //주문 목록 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "주문 목록 삭제", description = "주문 목록 하나를 삭제합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류")
    public ResponseEntity<ApiResponse> remove(
            @Parameter(required = true,description = "주문 목록 고유 번호")
            @PathVariable("id") Long id) {
        try {
            orderItemService.remove(id);
            return ResponseEntity.ok(ApiResponse.success(null)); // 성공 응답
        } catch (OrderItemTaskException e) {
            if (e.getMessage().equals(OrderItemException.NOT_FOUND.get().getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("주문 목록을 찾을 수 없습니다.")); // 404 Not Found 응답
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("주문 목록 삭제 중 오류가 발생했습니다."));
        }
    }

}







