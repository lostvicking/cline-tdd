package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring Boot application for Fibonacci calculator
 */
@SpringBootApplication
@EnableCaching
public class FibonacciApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FibonacciApplication.class, args);
    }
}
