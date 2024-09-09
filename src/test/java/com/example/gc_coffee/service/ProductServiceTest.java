package com.example.gc_coffee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.gc_coffee.dto.request.ProductCreateRequest;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.entity.ProductCategory;
import com.example.gc_coffee.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 전체 조회 테스트")
    void retrieveTest() {
        // given
        // 상품1 생성
        ProductCreateRequest request1 = new ProductCreateRequest(
                ProductCategory.BRAZIL_SERRA_DO_COFFEE,
                "브라질 세라도 커피",
                6500,
                "브라질 맛좋은 커피"
        );
        Product product1 = Product.create(
                request1.getProductCategory(),
                request1.getProductName(),
                request1.getPrice(),
                request1.getDescription()
        );

        // 상품2 생성
        ProductCreateRequest request2 = new ProductCreateRequest(
                ProductCategory.COLUMBIA_COFFEE,
                "콜롬비아 커피",
                5000,
                "콜롬비아 맛좋은 커피"
        );
        Product product2 = Product.create(
                request2.getProductCategory(),
                request2.getProductName(),
                request2.getPrice(),
                request2.getDescription()
        );

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // when
        List<Product> response = productService.retrieveAll();

        // then
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getProductName()).isEqualTo("브라질 세라도 커피");
        assertThat(response.get(1).getProductName()).isEqualTo("콜롬비아 커피");
    }
}