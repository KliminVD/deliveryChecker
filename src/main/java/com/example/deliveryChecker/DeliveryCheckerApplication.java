package com.example.deliveryChecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.deliveryChecker.model") // Сканирование сущностей
@EnableJpaRepositories(basePackages = "com.example.deliveryChecker.repository") // Сканирование репозиториев
public class DeliveryCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryCheckerApplication.class, args);
	}

}
