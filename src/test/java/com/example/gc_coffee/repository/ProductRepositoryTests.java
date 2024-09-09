package com.example.gc_coffee.repository;

import com.example.gc_coffee.entity.ProductCategory;
import com.example.gc_coffee.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test   //Insert 테스트
    public void testInsert(){
        //GIVEN
        ProductEntity product = ProductEntity.builder().productName("Columbia Coffee")
                .productCategory(ProductCategory.COLUMBIA_COFFEE)
                .price(10000)
                .description("콜롬비아의 맛있는 커피")
                .build();

        // WHEN - 엔티티 저장
        ProductEntity savedProduct = productRepository.save(product);

        // THEN - savedTodo가 널이 아니고 Id는 1일것
        assertNotNull(savedProduct);
        assertEquals(1, savedProduct.getId());
    }

    @Test   //SELECT 테스트
    public void testFindById(){
        //given     //@Id 타입의 값으로 엔티티 조회
        Long pid = 1L;

        Optional<ProductEntity> foundProduct = productRepository.findById(pid);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        // THEN
        assertNotNull(foundProduct);
        assertEquals(pid, foundProduct.get().getId());

        log.info("foundProduct : " + foundProduct);
        log.info("pid : " + foundProduct.get().getId());
    }

    @Test   //UPDATE 테스트 - 트랜잭션 O
    @Transactional
    @Commit
    public void testUpdateTransactional(){
        Long pid = 1L;
        Optional<ProductEntity> foundProduct = productRepository.findById(pid);

        foundProduct.get().changeProductName("decaf Columbia Coffee");
        foundProduct.get().changePrice(20000);
        foundProduct.get().changeDescription("씁쓸한 커피");


        assertEquals("decaf Columbia Coffee", foundProduct.get().getProductName());
        assertEquals(20000, foundProduct.get().getPrice());
        assertEquals("씁쓸한 커피", foundProduct.get().getDescription());
    }

    @Test   //DELETE 테스트 - 트랜잭션 O
    @Transactional
    @Commit
    public void testDelete() {
        Long pid = 1L;
        productRepository.deleteById(pid);

        Optional<ProductEntity> foundProduct = productRepository.findById(pid);
        assertTrue( foundProduct.isEmpty() );
    }







}
