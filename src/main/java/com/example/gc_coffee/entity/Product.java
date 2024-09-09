package com.example.gc_coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "product")
@Entity
@Builder                //빌더 패턴 어노테이션 추가
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String description;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    //setter 설정
    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Product(
            Long id,
            String productName,
            ProductCategory productCategory,
            Integer price,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Product create(
            final ProductCategory productCategory,
            final String productName,
            final Integer price,
            final String description
    ) {
        return Product.builder()
                .productCategory(productCategory)
                .productName(productName)
                .price(price)
                .description(description)
                .build();
    }

}
