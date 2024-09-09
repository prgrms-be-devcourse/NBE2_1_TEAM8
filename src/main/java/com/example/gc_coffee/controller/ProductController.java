package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.ProductCreateRequest;
import com.example.gc_coffee.dto.response.ProductResponse;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponse> register(
            @RequestBody @Valid final ProductCreateRequest request
    ) {
        productService.register(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse> retrieveAll() {
        List<Product> productList = productService.retrieveAll();

        // List<Product>를 List<ProductResponse>로 변환
        List<ProductResponse> response = productList.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList()); // 결과를 List로 수집
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
