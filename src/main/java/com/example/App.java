package com.example;

/**
 * Hello world application
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
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
     */
    public int getNextFibonacci(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        
        // Calculate Fibonacci number at the given index
        int current = calculateFibonacci(index);
        
        // Return the next Fibonacci number
        return calculateFibonacci(index + 1);
    }
    
    /**
     * Calculates the Fibonacci number at the given index
     * @param n the position in the Fibonacci sequence (0-based)
     * @return the Fibonacci number at the given position
     */
    private int calculateFibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        
        int prev = 0;
        int current = 1;
        
        for (int i = 2; i <= n; i++) {
            int next = prev + current;
            prev = current;
            current = next;
        }
        
        return current;
    }
}
