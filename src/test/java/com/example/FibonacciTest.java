package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Unit test for Fibonacci sequence functionality
 */
public class FibonacciTest {
    
    @Test
    public void shouldReturnOneForIndexZero() {
        App app = new App();
        
        // The next Fibonacci number after F(0)=0 is F(1)=1
        assertThat(app.getNextFibonacci(0))
            .isEqualTo(1);
    }
    
    @Test
    public void shouldReturnTwoForIndexOne() {
        App app = new App();
        
        // The next Fibonacci number after F(1)=1 is F(2)=1
        assertThat(app.getNextFibonacci(1))
            .isEqualTo(1);
    }
    
    @ParameterizedTest
    @CsvSource({
        "2, 2",  // After F(2)=1 comes F(3)=2
        "3, 3",  // After F(3)=2 comes F(4)=3
        "4, 5",  // After F(4)=3 comes F(5)=5
        "5, 8",  // After F(5)=5 comes F(6)=8
        "6, 13", // After F(6)=8 comes F(7)=13
        "7, 21", // After F(7)=13 comes F(8)=21
        "8, 34"  // After F(8)=21 comes F(9)=34
    })
    public void shouldReturnCorrectNextFibonacciNumber(int index, int expectedNext) {
        App app = new App();
        
        assertThat(app.getNextFibonacci(index))
            .as("Next Fibonacci number after index %d", index)
            .isEqualTo(expectedNext);
    }
    
    @Test
    public void shouldThrowExceptionForNegativeIndex() {
        App app = new App();
        
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> app.getNextFibonacci(-1))
            .withMessageContaining("negative");
    }
    
    @Test
    public void shouldVerifyFibonacciSequenceProperty() {
        App app = new App();
        
        // For any index n, F(n+2) = F(n) + F(n+1)
        // So getNextFibonacci(n+1) = getNextFibonacci(n-1) + getNextFibonacci(n)
        int n = 5; // Choose an arbitrary index
        
        int fibNPlus2 = app.getNextFibonacci(n+1); // F(n+2)
        int fibN = app.getNextFibonacci(n-1);      // F(n)
        int fibNPlus1 = app.getNextFibonacci(n);   // F(n+1)
        
        assertThat(fibNPlus2)
            .as("F(n+2) should equal F(n) + F(n+1)")
            .isEqualTo(fibN + fibNPlus1);
    }
}
