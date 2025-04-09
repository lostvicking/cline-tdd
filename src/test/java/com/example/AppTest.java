package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit and integration tests for the Spring Boot application
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AppTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private App app;
    
    @Autowired
    private FibonacciController controller;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void contextLoads() {
        assertThat(app).isNotNull();
        assertThat(controller).isNotNull();
    }
    
    @Test
    public void shouldReturnCorrectGreeting() {
        // Using AssertJ for assertions as specified in custom instructions
        assertThat(app.getGreeting())
            .isNotNull()
            .isEqualTo("Hello World!");
    }
    
    @Test
    public void shouldReturnFibonacciNumberViaRest() {
        // Test the REST endpoint for getting a Fibonacci number
        String url = "http://localhost:" + port + "/api/fibonacci/10";
        ResponseEntity<FibonacciController.FibonacciResponse> response = 
            restTemplate.getForEntity(url, FibonacciController.FibonacciResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIndex()).isEqualTo(10);
        assertThat(response.getBody().getValue()).isEqualTo(55); // F(10) = 55
    }
    
    @Test
    public void shouldReturnNextFibonacciNumberViaRest() {
        // Test the REST endpoint for getting the next Fibonacci number
        String url = "http://localhost:" + port + "/api/fibonacci/next/10";
        ResponseEntity<FibonacciController.FibonacciResponse> response = 
            restTemplate.getForEntity(url, FibonacciController.FibonacciResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIndex()).isEqualTo(11);
        assertThat(response.getBody().getValue()).isEqualTo(89); // F(11) = 89
    }
    
    @Test
    public void shouldReturnFibonacciSequenceViaRest() {
        // Test the REST endpoint for getting a sequence of Fibonacci numbers
        String url = "http://localhost:" + port + "/api/fibonacci/sequence?start=5&count=5";
        ResponseEntity<FibonacciController.FibonacciSequenceResponse> response = 
            restTemplate.getForEntity(url, FibonacciController.FibonacciSequenceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStart()).isEqualTo(5);
        assertThat(response.getBody().getCount()).isEqualTo(5);
        assertThat(response.getBody().getSequence()).containsExactly(5, 8, 13, 21, 34);
    }
    
    @Test
    public void shouldReturnBadRequestForNegativeIndex() {
        // Test error handling for negative index
        String url = "http://localhost:" + port + "/api/fibonacci/-1";
        ResponseEntity<FibonacciController.FibonacciResponse> response = 
            restTemplate.getForEntity(url, FibonacciController.FibonacciResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("negative");
    }
}
