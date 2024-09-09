package com.example.gc_coffee.service;

import com.example.gc_coffee.dto.request.ProductUpdateRequest;
import com.example.gc_coffee.entity.Product;
import com.example.gc_coffee.entity.ProductCategory;
import com.example.gc_coffee.exception.ProductTaskException;
import com.example.gc_coffee.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Log4j2
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testModify() {
        // given
        Long productId = 1L;

        // 빌더 패턴을 사용한 Product 객체 생성
        Product existingProduct = Product.builder()
                .id(productId)
                .productName("Old Coffee")
                .price(30000)
                .productCategory(ProductCategory.COLUMBIA_QUINDIO)
                .description("기존 커피")
                .build();

        ProductUpdateRequest modifiedProduct = new ProductUpdateRequest();
        modifiedProduct.setPid(productId);
        modifiedProduct.setProductName("Test Coffee");
        modifiedProduct.setPrice(50000);
        modifiedProduct.setProductCategory(ProductCategory.COLUMBIA_COFFEE);
        modifiedProduct.setDescription("시음용 커피");

        // lenient()를 사용해 불필요한 stubbing 경고를 피함
        lenient().when(productRepository.save(existingProduct)).thenThrow(new RuntimeException("Database error"));

        // when & then
        assertThrows(ProductTaskException.class, () -> productService.modify(modifiedProduct));

        // then
        assertNotNull(modifiedProduct);
        assertEquals("Test Coffee", modifiedProduct.getProductName());
        assertEquals("시음용 커피", modifiedProduct.getDescription());
        log.info(modifiedProduct);
    }

    @Test
    public void testRemove() {
        // given
        Long productId = 1L;

        // 빌더 패턴을 사용한 Product 객체 생성
        Product existingProduct = Product.builder()
                .id(productId)
                .productName("Old Coffee")
                .productCategory(ProductCategory.COLUMBIA_QUINDIO)
                .price(30000)
                .build();

        // Mock 설정: findById가 Optional.of(existingProduct)를 반환하도록 설정
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // when & then
        assertDoesNotThrow(() -> productService.remove(productId));

        // Verify that the product was deleted
        verify(productRepository).delete(existingProduct);

        // 로그 출력으로 삭제 확인
        log.info("Product with ID {} was removed successfully.", productId);
    }



}
