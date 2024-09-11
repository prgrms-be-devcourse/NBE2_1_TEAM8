package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequest {

    // 수정할 주문 항목의 ID와 수량
    private Map<Long, Integer> itemQuantityUpdates;


}