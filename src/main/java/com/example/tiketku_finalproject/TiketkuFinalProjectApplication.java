package com.example.tiketku_finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class TiketkuFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiketkuFinalProjectApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins(
								"http://localhost:8080", //ini portnya sesuai dari railway
								"http://localhost:3001",
								"https://binar-backend-final-project-production.up.railway.app",
								"*");
			}
		};
	}

	@Bean
	public void sayHello() {
		System.out.println("hello world");
	}
}
