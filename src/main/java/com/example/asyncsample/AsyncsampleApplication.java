package com.example.asyncsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncsampleApplication.class, args);
	}

}
