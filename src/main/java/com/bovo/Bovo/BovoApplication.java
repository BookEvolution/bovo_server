package com.bovo.Bovo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.bovo.Bovo.common")
public class BovoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BovoApplication.class, args);
	}

}
