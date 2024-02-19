package com.example.socialmedia;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.socialmedia")
@EntityScan(basePackages = {"com.example.socialmedia.model", "com.example.socialmedia.followTables","com.example.socialmedia.feed"}) // Adjust the package accordingly
// Adjust the package accordingly
@EnableJpaRepositories(basePackages = "com.example.socialmedia.Repository")
@EnableCaching
public class SocialmediaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialmediaApplication.class, args);
	}
}
