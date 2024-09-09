package com.example.gc_coffee.dto.response;

import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductResponse {

    private Long productId;
    private String productName;
    private ProductCategory category;
    private Integer price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Product 엔티티로부터 ProductResponse를 생성하는 메서드
    public static ProductResponse from(final Product product) {
        ProductResponse response = new ProductResponse();
        response.productId = product.getId();
        response.productName = product.getProductName();
        response.category = product.getProductCategory();
        response.price = product.getPrice();
        response.description = product.getDescription();
        response.createdAt = product.getCreatedAt();
        response.updatedAt = product.getUpdatedAt();
        return response;
    }
}

