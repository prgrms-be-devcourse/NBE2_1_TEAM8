package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "상품 등록 시 기재할 내용 지정")
public class ProductCreateRequest {
    @Schema(description = "등록할 상품 카테고리")
    @NotNull(message = "상품 카테고리 기재는 필수입니다.")
    private final ProductCategory productCategory;

    @Schema(description = "등록할 상품 이름")
    @NotBlank(message = "상품 이름을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String productName;

    @Schema(description = "등록할 상품 가격")
    @NotNull(message = "가격은 필수 기재 사항입니다.")
    private final Integer price;

    @Schema(description = "등록할 상품 설명")
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
