package com.example.gc_coffee.dto.request;

import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "상품 수정 시 수정할 내용 지정")
public class ProductUpdateRequest {
    // 제품 id
    @Schema(description = "수정할 상품 번호(파라미터로 입력한 id와 동일 id이어야 한다.)")
    private Long pid;

    // 제품 카테고리
    @Schema(description = "수정할 상품 카테고리")
    @NotNull(message = "상품 유형은 기재는 필수입니다.")
    private ProductCategory productCategory;

    // 제품명
    @Schema(description = "수정할 상품 명")
    @NotBlank(message = "상품명을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private String productName;

    // 가격
    @Schema(description = "수정할 상품 가격")
    @NotNull(message = "가격은 필수 기재 사항입니다.")
    private Integer price;

    // 설명
    @Schema(description = "수정할 상품 설명")
    @NotBlank(message = "설명을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private String description;

    // Product 객체로부터 업데이트 요청 생성자
    public ProductUpdateRequest(Product product) {
        this.pid = product.getId();
        this.productCategory = product.getProductCategory();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    // Product 엔티티로 변환하는 메서드
    public Product toEntity() {
        return Product.builder()
                .productCategory(productCategory)
                .productName(productName)
                .price(price)
                .description(description)
                .build();
    }

}
