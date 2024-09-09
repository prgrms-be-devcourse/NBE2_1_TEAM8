package com.example.gc_coffee.service;

import com.example.gc_coffee.dto.request.ProductCreateRequest;
import com.example.gc_coffee.dto.request.ProductUpdateRequest;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.exception.ProductException;
import com.example.gc_coffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public void register(final ProductCreateRequest request) {
        Product product = Product.create(
                request.getProductName(),
                request.getProductCategory(),
                request.getPrice(),
                request.getDescription()
        );
        productRepository.save(product);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public ProductUpdateRequest modify(ProductUpdateRequest request) {
        // 수정하려는 상품을 데이터베이스에서 조회
        Product product = productRepository.findById(request.getPid())
                .orElseThrow(() -> ProductException.NOT_FOUND.get());

        try {
            // 상품 이름 수정
            if (request.getProductName() != null) {
                product.changeProductName(request.getProductName());
            }
            // 상품 가격 수정
            if (request.getPrice() != null) {
                product.changePrice(request.getPrice());
            }
            // 상품 설명 수정
            if (request.getDescription() != null) {
                product.changeDescription(request.getDescription());
            }
            // 상품 카테고리 수정
            if (request.getProductCategory() != null) {
                product.changeProductCategory(request.getProductCategory());
            }

            // 변경된 상품을 저장 (자동으로 변경 감지 후 DB에 반영)
            productRepository.save(product);

            // 수정된 내용을 다시 반환
            return new ProductUpdateRequest(product);

        } catch (Exception e) {
            log.error("Error modifying product: " + e.getMessage());
            throw ProductException.NOT_MODIFIED.get();
        }
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void remove(Long id) {
        // 수정하려는 상품을 데이터베이스에서 조회
        Optional<Product> optionalProduct = productRepository.findById(id);
        log.info("Product found: {}", optionalProduct.isPresent());

        Product product = optionalProduct.orElseThrow(() -> ProductException.NOT_FOUND.get());

        try {
            // 상품 삭제
            productRepository.delete(product);
        } catch (Exception e) {
            log.error("Error removing product: " + e.getMessage());
            throw ProductException.NOT_REMOVED.get();
        }
    }
}
