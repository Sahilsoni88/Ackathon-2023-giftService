package com.giftservice.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class GiftCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiftCardApplication.class, args);
	}

}
