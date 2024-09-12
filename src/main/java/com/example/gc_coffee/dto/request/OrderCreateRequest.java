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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class OrderCreateRequest {
    //주문 id
    private Long orderId;

    @NotBlank(message = "Email은 비어있을 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "주소는 비어있을 수 없습니다.")
    private String address;

    @NotBlank(message = "우편번호는 비어있을 수 없습니다.")
    private String postcode;

    @NotEmpty(message = "주문 항목은 비어있을 수 없습니다.")
    private List<OrderItemCreateRequest> orderItems;

    public OrderCreateRequest(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postcode = order.getPostcode();

    }
}
