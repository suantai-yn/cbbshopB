package com.example.cbbshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
@MapperScan("com.example.cbbshop.mapper")
public class CbbshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CbbshopApplication.class, args);
		System.out.println("Current working directory: " + Paths.get("").toAbsolutePath());

	}

}
