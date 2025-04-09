package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Legacy App class maintained for backward compatibility
 * Now uses the Spring-managed FibonacciCalculator service
 */
@Component
public class App {
    
    private final FibonacciCalculator fibonacciCalculator;
    
    @Autowired
    public App(FibonacciCalculator fibonacciCalculator) {
        this.fibonacciCalculator = fibonacciCalculator;
    }
    
    /**
     * Default constructor for backward compatibility
     * Note: This will not have the Spring-managed FibonacciCalculator
     * and should only be used in non-Spring contexts
     */
    public App() {
        this.fibonacciCalculator = new FibonacciCalculator();
    }
    
    /**
     * Returns a greeting message
     * @return greeting message
     */
    public String getGreeting() {
        return "Hello World!";
    }
    
    /**
     * Returns the next number in the Fibonacci sequence after the given index
     * @param index the position in the Fibonacci sequence (0-based)
     * @return the next Fibonacci number after the given index
     * @throws IllegalArgumentException if index is negative
     * @throws ArithmeticException if the result is too large for int
     */
    public int getNextFibonacci(int index) {
        long result = fibonacciCalculator.getNextFibonacci(index);
        
        // Check if the result fits in an int
        if (result > Integer.MAX_VALUE) {
            throw new ArithmeticException("Fibonacci number too large for int type");
        }
        
        return (int) result;
    }
    
    /**
     * Returns the next number in the Fibonacci sequence after the given index as a long
     * This method can handle larger Fibonacci numbers than getNextFibonacci
     * 
     * @param index the position in the Fibonacci sequence (0-based)
     * @return the next Fibonacci number after the given index
     * @throws IllegalArgumentException if index is negative
     */
    public long getNextFibonacciLong(int index) {
        return fibonacciCalculator.getNextFibonacci(index);
    }
    
    /**
     * Calculates the Fibonacci number at the given index
     * 
     * @param n the position in the Fibonacci sequence (0-based)
     * @return the Fibonacci number at the given position
     * @throws IllegalArgumentException if index is negative
     * @throws ArithmeticException if the result is too large for int
     */
    public int calculateFibonacci(int n) {
        long result = fibonacciCalculator.calculateFibonacci(n);
        
        // Check if the result fits in an int
        if (result > Integer.MAX_VALUE) {
            throw new ArithmeticException("Fibonacci number too large for int type");
        }
        
        return (int) result;
    }
    
    /**
     * Calculates the Fibonacci number at the given index as a long
     * This method can handle larger Fibonacci numbers than calculateFibonacci
     * 
     * @param n the position in the Fibonacci sequence (0-based)
     * @return the Fibonacci number at the given position
     * @throws IllegalArgumentException if index is negative
     */
    public long calculateFibonacciLong(int n) {
        return fibonacciCalculator.calculateFibonacci(n);
    }
}
