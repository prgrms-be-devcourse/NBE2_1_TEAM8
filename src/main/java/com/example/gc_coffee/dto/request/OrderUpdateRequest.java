package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주문 항목 수량 수정 요청")
public class OrderUpdateRequest {

    @Schema(description = "수정할 주문 항목 ID와 그 항목의 새로운 수량을 설정합니다. (주문 항목 0으로 변경 시 삭제)")

    private Map<Long, Integer> itemQuantityUpdates;


}