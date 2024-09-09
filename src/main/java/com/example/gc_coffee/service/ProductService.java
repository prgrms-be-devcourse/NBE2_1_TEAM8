package com.example.gc_coffee.service;

import com.example.gc_coffee.dto.request.ProductCreateRequest;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 프로모션 등록
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
}
