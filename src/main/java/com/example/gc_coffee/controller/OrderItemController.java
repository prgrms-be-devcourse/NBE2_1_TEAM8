package com.example.gc_coffee.controller;

<<<<<<< feat/#39/주문-수정-및-삭제-기능-구현
import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.OrderCreateRequest;
import com.example.gc_coffee.dto.request.OrderItemModificationRequest;
import com.example.gc_coffee.dto.request.OrderItemUpdateRequest;
import com.example.gc_coffee.exception.OrderException;
import com.example.gc_coffee.exception.OrderItemException;
import com.example.gc_coffee.exception.OrderItemTaskException;
import com.example.gc_coffee.exception.OrderTaskException;
import com.example.gc_coffee.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;

    //주문 목록 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> modify(
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
    public ResponseEntity<ApiResponse> remove(@PathVariable("id") Long id) {
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




=======
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
>>>>>>> main
