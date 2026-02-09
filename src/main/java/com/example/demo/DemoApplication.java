package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Value("${spring.data.mongodb.database}") private String dbName;


	@Override
	public void run(String... args) throws Exception {
		System.out.println(">>> Base MongoDB utilisée : " + dbName);
	}
}
