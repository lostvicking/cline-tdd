package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Unit test for Fibonacci sequence functionality
 */
public class FibonacciTest {
    
    private App app;
    private FibonacciCalculator calculator;
    
    @BeforeEach
    public void setup() {
        app = new App();
        calculator = new FibonacciCalculator();
    }
    
    @Test
    public void shouldReturnOneForIndexZero() {
        // The next Fibonacci number after F(0)=0 is F(1)=1
        assertThat(app.getNextFibonacci(0))
            .as("Next Fibonacci number after index 0")
            .isEqualTo(1);
    }
    
    @Test
    public void shouldReturnOneForIndexOne() {
        // The next Fibonacci number after F(1)=1 is F(2)=1
        assertThat(app.getNextFibonacci(1))
            .as("Next Fibonacci number after index 1")
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
        assertThat(app.getNextFibonacci(index))
            .as("Next Fibonacci number after index %d", index)
            .isEqualTo(expectedNext);
    }
    
    @Test
    public void shouldThrowExceptionForNegativeIndex() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> app.getNextFibonacci(-1))
            .withMessageContaining("negative");
    }
    
    @Test
    public void shouldVerifyFibonacciSequenceProperty() {
        // For any index n, F(n+2) = F(n) + F(n+1)
        int n = 5; // Choose an arbitrary index
        
        int fibNPlus2 = app.getNextFibonacci(n+1); // F(n+2)
        int fibN = app.getNextFibonacci(n-1);      // F(n)
        int fibNPlus1 = app.getNextFibonacci(n);   // F(n+1)
        
        assertThat(fibNPlus2)
            .as("F(n+2) should equal F(n) + F(n+1)")
            .isEqualTo(fibN + fibNPlus1);
    }
    
    @Test
    public void shouldHandleLargerIndices() {
        // Test with a larger index that still fits in int
        int largeIndex = 30;
        
        // Calculate expected result using the formula
        long expected = 1346269;  // F(31) = 1,346,269
        
        assertThat(app.getNextFibonacci(largeIndex))
            .as("Next Fibonacci number after index %d", largeIndex)
            .isEqualTo((int)expected);
    }
    
    @Test
    public void shouldHandleVeryLargeIndicesWithLongMethod() {
        // Test with a larger index that requires long
        int largeIndex = 70;
        
        // F(71) = 308,061,521,170,129
        // This is too large for int but fits in long
        assertThat(app.getNextFibonacciLong(largeIndex))
            .as("Next Fibonacci number after index %d (as long)", largeIndex)
            .isGreaterThan(Integer.MAX_VALUE)
            .isEqualTo(308061521170129L);
    }
    
    @Test
    public void shouldThrowExceptionForIntOverflow() {
        // F(47) = 2,971,215,073 which is larger than Integer.MAX_VALUE
        int overflowIndex = 46;
        
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> app.getNextFibonacci(overflowIndex))
            .withMessageContaining("too large for int");
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10, 20})
    public void calculatorAndAppShouldReturnSameResults(int index) {
        // Verify that both implementations return the same results
        assertThat(app.getNextFibonacci(index))
            .as("App and Calculator should return same result for index %d", index)
            .isEqualTo((int)calculator.getNextFibonacci(index));
    }
    
    @Test
    public void shouldUseMemoizationEffectively() {
        // This test verifies that memoization works by calling the method multiple times
        // and checking that it's still fast for repeated calls with the same index
        
        // First call might be slower as it calculates and caches values
        long start = System.nanoTime();
        long result1 = app.getNextFibonacciLong(40);
        long firstCallDuration = System.nanoTime() - start;
        
        // Second call should be much faster as it uses cached values
        start = System.nanoTime();
        long result2 = app.getNextFibonacciLong(40);
        long secondCallDuration = System.nanoTime() - start;
        
        // Verify results are the same
        assertThat(result1).isEqualTo(result2);
        
        // This assertion might be flaky in some environments, but generally
        // the second call should be significantly faster due to caching
        assertThat(secondCallDuration)
            .as("Second call should be faster due to memoization")
            .isLessThan(firstCallDuration);
    }
}
