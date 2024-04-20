package com.amazon.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class AmazonProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonProductServiceApplication.class, args);
	}

}
