package com.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for calculating Fibonacci numbers
 */
public class FibonacciCalculator {
    // Cache for memoization
    private final Map<Integer, Long> cache = new HashMap<>();
    
    /**
     * Constructor initializes the cache with base cases
     */
    public FibonacciCalculator() {
        // Initialize with base cases
        cache.put(0, 0L);
        cache.put(1, 1L);
    }
    
    /**
     * Returns the Fibonacci number at the given index
     * @param n the position in the Fibonacci sequence (0-based)
     * @return the Fibonacci number at the given position
     * @throws IllegalArgumentException if index is negative
     */
    public long calculateFibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        
        // Check if result is in cache
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        
        // For larger indices, use iterative approach to avoid stack overflow
        if (n > 50) {
            return calculateFibonacciIterative(n);
        }
        
        // Calculate recursively with memoization
        long result = calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
        
        // Check for overflow
        if (result < 0) {
            throw new ArithmeticException("Fibonacci number too large for long type");
        }
        
        // Cache the result
        cache.put(n, result);
        return result;
    }
    
    /**
     * Iterative implementation for calculating Fibonacci numbers
     * Used for larger indices to avoid stack overflow
     */
    private long calculateFibonacciIterative(int n) {
        // Start from the highest cached value
        int start = 0;
        long prev = 0;
        long current = 1;
        
        // Find the highest cached value to start from
        for (int i = 2; i <= Math.min(n, 50); i++) {
            if (cache.containsKey(i)) {
                start = i;
                current = cache.get(i);
                prev = cache.get(i - 1);
            }
        }
        
        // Calculate remaining values iteratively
        for (int i = start + 1; i <= n; i++) {
            long next = prev + current;
            
            // Check for overflow
            if (next < 0) {
                throw new ArithmeticException("Fibonacci number too large for long type");
            }
            
            prev = current;
            current = next;
            
            // Cache intermediate results up to a reasonable limit
            if (i <= 1000) {
                cache.put(i, current);
            }
        }
        
        return current;
    }
    
    /**
     * Returns the next number in the Fibonacci sequence after the given index
     * @param index the position in the Fibonacci sequence (0-based)
     * @return the next Fibonacci number after the given index
     * @throws IllegalArgumentException if index is negative
     */
    public long getNextFibonacci(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        
        // Simply return the Fibonacci number at index + 1
        return calculateFibonacci(index + 1);
    }
}
