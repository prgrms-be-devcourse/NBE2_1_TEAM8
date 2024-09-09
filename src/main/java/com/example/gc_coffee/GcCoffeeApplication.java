package com.example.gc_coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 엔티티 시간 자동처리 설정
public class GcCoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcCoffeeApplication.class, args);
	}

}
