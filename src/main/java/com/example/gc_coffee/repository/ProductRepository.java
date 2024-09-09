package com.example.gc_coffee.repository;


import com.example.gc_coffee.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Jpa 기능을 활용하기 위해서 JpaRepository 상속
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
