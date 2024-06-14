package com.northcoders.recordshopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecordshopapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordshopapiApplication.class, args);
	}

}
