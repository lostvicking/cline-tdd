package com.example;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

/**
 * Service for calculating Fibonacci numbers
 */
@Service
@Validated
public class FibonacciCalculator {
    
    /**
     * Returns the Fibonacci number at the given index
     * Uses Spring's caching mechanism for memoization
     * 
     * @param n the position in the Fibonacci sequence (0-based)
     * @return the Fibonacci number at the given position
     * @throws IllegalArgumentException if index is negative
     */
    @Cacheable("fibonacci")
    public long calculateFibonacci(@Min(0) int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        
        if (n <= 1) {
            return n;
        }
        
        // For larger indices, use iterative approach to avoid stack overflow
        if (n > 50) {
            return calculateFibonacciIterative(n);
        }
        
        // Calculate recursively
        // Since we're using Spring's caching, we don't need our own cache map
        long result = calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
        
        // Check for overflow
        if (result < 0) {
            throw new ArithmeticException("Fibonacci number too large for long type");
        }
        
        return result;
    }
    
    /**
     * Iterative implementation for calculating Fibonacci numbers
     * Used for larger indices to avoid stack overflow
     */
    private long calculateFibonacciIterative(int n) {
        if (n <= 1) {
            return n;
        }
        
        long prev = 0;
        long current = 1;
        
        for (int i = 2; i <= n; i++) {
            long next = prev + current;
            
            // Check for overflow
            if (next < 0) {
                throw new ArithmeticException("Fibonacci number too large for long type");
            }
            
            prev = current;
            current = next;
        }
        
        return current;
    }
    
    /**
     * Returns the next number in the Fibonacci sequence after the given index
     * 
     * @param index the position in the Fibonacci sequence (0-based)
     * @return the next Fibonacci number after the given index
     * @throws IllegalArgumentException if index is negative
     */
    @Cacheable("nextFibonacci")
    public long getNextFibonacci(@Min(0) int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        
        // Simply return the Fibonacci number at index + 1
        return calculateFibonacci(index + 1);
    }
}
