package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    // 제품 카테고리
    @NotNull(message = "상품 유형은 기재는 필수입니다.")
    private final ProductCategory productCategory;

    // 제품명
    @NotBlank(message = "상품명을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String productName;

    // 가격
    @NotNull(message = "가격은 필수 기재 사항입니다.")
    private final Integer price;

    // 설명
    @NotBlank(message = "설명을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String description;

    public ProductCreateRequest(
            final ProductCategory productCategory,
            final String productName,
            final Integer price,
            final String description
    ) {
        this.productCategory = productCategory;
        this.productName = productName;
        this.price = price;
        this.description = description;
    }

}
