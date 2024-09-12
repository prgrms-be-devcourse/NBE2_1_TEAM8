package com.example.gc_coffee.controller;

import com.example.gc_coffee.common.ApiResponse;
import com.example.gc_coffee.dto.request.ProductCreateRequest;

import com.example.gc_coffee.dto.request.ProductUpdateRequest;
import com.example.gc_coffee.exception.ProductException;
import com.example.gc_coffee.exception.ProductTaskException;
import com.example.gc_coffee.dto.response.ProductResponse;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@Tag(name = "Product" , description = "상품 관련 API" )
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 등록
     */
    @Operation(summary = "상품 등록" , description = "상품 카테고리, 상품 이름, 가격, 설명을 기재합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse> register(
            @RequestBody @Valid final ProductCreateRequest request
    ) {
        productService.register(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**

     * 상품 수정
     */
    @Operation(summary = "상품 수정" , description = "상품 이름, 설명, 카테고리, 가격을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> modify(
            @Parameter(description = "수정할 상품 id를 입력하세요.")
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductUpdateRequest request
    ) {
        // 수정 요청의 ID와 PathVariable ID가 일치하는지 확인
        if (!id.equals(request.getPid())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("상품 ID가 일치하지 않습니다."));
        }

        ProductUpdateRequest updatedProduct;
        try {
            updatedProduct = productService.modify(request);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (ProductTaskException e) {
            if (e.getMessage().equals(ProductException.NOT_FOUND.get().getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("상품을 찾을 수 없습니다."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("상품 수정 중 오류가 발생했습니다."));
        }
    }

    /**
     * 상품 삭제
     */
    @Operation(summary = "상품 삭제" , description = "등록된 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> remove(
                            @Parameter(description = "삭제할 상품 id를 입력하세요.")
                            @PathVariable("id") Long id) {
        try {
            productService.remove(id);
            return ResponseEntity.ok(ApiResponse.success(null)); // 성공 응답
        } catch (ProductTaskException e) {
            if (e.getMessage().equals(ProductException.NOT_FOUND.get().getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("상품을 찾을 수 없습니다.")); // 404 Not Found 응답
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("상품 삭제 중 오류가 발생했습니다."));
        }
    }




    /**
     * 상품 전체 조회
     */
    @Operation(summary = "상품 전체 조회" , description = "상품 번호, 상품 이름, 카테고리, 가격, 설명, 생성 시간, 수정 시간을 " +
            "리스트 형태로 조회합니다.")
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
