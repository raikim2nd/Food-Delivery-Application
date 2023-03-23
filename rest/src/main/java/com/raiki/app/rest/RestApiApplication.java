package com.raiki.app.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestApiApplication {



	public static void main(String[] args) throws Exception {
		SpringApplication.run(RestApiApplication.class, args);
	}
}
