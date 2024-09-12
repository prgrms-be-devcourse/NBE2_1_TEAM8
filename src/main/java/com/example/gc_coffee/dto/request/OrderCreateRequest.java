package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class OrderCreateRequest {

    @Schema(description = "주문 ID", example = "1")
    private Long orderId;

    @NotBlank(message = "Email은 비어있을 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Schema(description = "주문자 이메일", example = "example@example.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "주소는 비어있을 수 없습니다.")
    @Schema(description = "배송 주소", example = "서울특별시 강남구 역삼동", requiredMode = RequiredMode.REQUIRED)
    private String address;

    @NotBlank(message = "우편번호는 비어있을 수 없습니다.")
    @Schema(description = "우편번호", example = "06130", requiredMode = RequiredMode.REQUIRED)
    private String postcode;

    @NotEmpty(message = "주문 항목은 비어있을 수 없습니다.")
    @Schema(description = "주문 항목 리스트", requiredMode = RequiredMode.REQUIRED)
    private List<OrderItemCreateRequest> orderItems;

    public OrderCreateRequest(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();

    }
}
