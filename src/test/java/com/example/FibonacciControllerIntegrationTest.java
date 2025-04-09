package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the Fibonacci REST API
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FibonacciControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void shouldReturnFibonacciNumber() throws Exception {
        mockMvc.perform(get("/api/fibonacci/10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.index", is(10)))
            .andExpect(jsonPath("$.value", is(55)))
            .andExpect(jsonPath("$.message", is("F(10)")));
    }
    
    @Test
    public void shouldReturnNextFibonacciNumber() throws Exception {
        mockMvc.perform(get("/api/fibonacci/next/10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.index", is(11)))
            .andExpect(jsonPath("$.value", is(89)))
            .andExpect(jsonPath("$.message", containsString("Next after")));
    }
    
    @Test
    public void shouldReturnFibonacciSequence() throws Exception {
        mockMvc.perform(get("/api/fibonacci/sequence")
                .param("start", "5")
                .param("count", "5"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.start", is(5)))
            .andExpect(jsonPath("$.count", is(5)))
            .andExpect(jsonPath("$.sequence", hasSize(5)))
            .andExpect(jsonPath("$.sequence", contains(5, 8, 13, 21, 34)));
    }
    
    @Test
    public void shouldReturnBadRequestForNegativeIndex() throws Exception {
        mockMvc.perform(get("/api/fibonacci/-1"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("negative")));
    }
    
    @Test
    public void shouldReturnBadRequestForTooLargeCount() throws Exception {
        mockMvc.perform(get("/api/fibonacci/sequence")
                .param("start", "0")
                .param("count", "101"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error", containsString("Count cannot exceed 100")));
    }
    
    @Test
    public void shouldHandleDefaultParameters() throws Exception {
        mockMvc.perform(get("/api/fibonacci/sequence"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.start", is(0)))
            .andExpect(jsonPath("$.count", is(10)))
            .andExpect(jsonPath("$.sequence", hasSize(10)));
    }
}
