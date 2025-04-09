package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

/**
 * REST controller for Fibonacci operations
 */
@RestController
@RequestMapping("/api/fibonacci")
@Validated
public class FibonacciController {
    
    private final FibonacciCalculator fibonacciCalculator;
    
    @Autowired
    public FibonacciController(FibonacciCalculator fibonacciCalculator) {
        this.fibonacciCalculator = fibonacciCalculator;
    }
    
    /**
     * Get the Fibonacci number at the specified index
     * 
     * @param index the position in the Fibonacci sequence (0-based)
     * @return the Fibonacci number at the given position
     */
    @GetMapping("/{index}")
    public ResponseEntity<FibonacciResponse> getFibonacciNumber(
            @PathVariable int index) {
        
        if (index < 0) {
            return ResponseEntity.badRequest().body(
                new FibonacciResponse(index, -1, "Index cannot be negative"));
        }
        
        try {
            long result = fibonacciCalculator.calculateFibonacci(index);
            return ResponseEntity.ok(new FibonacciResponse(index, result));
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest().body(
                new FibonacciResponse(index, -1, "Overflow: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new FibonacciResponse(index, -1, "Error: " + e.getMessage()));
        }
    }
    
    /**
     * Get the next Fibonacci number after the specified index
     * 
     * @param index the position in the Fibonacci sequence (0-based)
     * @return the next Fibonacci number after the given index
     */
    @GetMapping("/next/{index}")
    public ResponseEntity<FibonacciResponse> getNextFibonacciNumber(
            @PathVariable int index) {
        
        if (index < 0) {
            return ResponseEntity.badRequest().body(
                new FibonacciResponse(index, -1, "Index cannot be negative"));
        }
        
        try {
            long result = fibonacciCalculator.getNextFibonacci(index);
            return ResponseEntity.ok(
                new FibonacciResponse(index + 1, result, "Next after F(" + index + ")"));
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest().body(
                new FibonacciResponse(index + 1, -1, "Overflow: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new FibonacciResponse(index + 1, -1, "Error: " + e.getMessage()));
        }
    }
    
    /**
     * Get a sequence of Fibonacci numbers starting from the specified index
     * 
     * @param start the starting position in the Fibonacci sequence (0-based)
     * @param count the number of Fibonacci numbers to return
     * @return a sequence of Fibonacci numbers
     */
    @GetMapping("/sequence")
    public ResponseEntity<FibonacciSequenceResponse> getFibonacciSequence(
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "10") int count) {
        
        if (start < 0) {
            return ResponseEntity.badRequest().body(
                new FibonacciSequenceResponse("Start index cannot be negative"));
        }
        
        if (count < 1) {
            return ResponseEntity.badRequest().body(
                new FibonacciSequenceResponse("Count must be at least 1"));
        }
        
        if (count > 100) {
            return ResponseEntity.badRequest().body(
                new FibonacciSequenceResponse("Count cannot exceed 100"));
        }
        
        try {
            long[] sequence = new long[count];
            for (int i = 0; i < count; i++) {
                sequence[i] = fibonacciCalculator.calculateFibonacci(start + i);
            }
            return ResponseEntity.ok(new FibonacciSequenceResponse(start, count, sequence));
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest().body(
                new FibonacciSequenceResponse("Overflow: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new FibonacciSequenceResponse("Error: " + e.getMessage()));
        }
    }
    
    /**
     * Response class for Fibonacci number requests
     */
    public static class FibonacciResponse {
        private int index;
        private long value;
        private String message;
        
        // Default constructor for JSON deserialization
        public FibonacciResponse() {
        }
        
        public FibonacciResponse(int index, long value) {
            this(index, value, "F(" + index + ")");
        }
        
        public FibonacciResponse(int index, long value, String message) {
            this.index = index;
            this.value = value;
            this.message = message;
        }
        
        public int getIndex() {
            return index;
        }
        
        public void setIndex(int index) {
            this.index = index;
        }
        
        public long getValue() {
            return value;
        }
        
        public void setValue(long value) {
            this.value = value;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    /**
     * Response class for Fibonacci sequence requests
     */
    public static class FibonacciSequenceResponse {
        private int start;
        private int count;
        private long[] sequence;
        private String error;
        
        // Default constructor for JSON deserialization
        public FibonacciSequenceResponse() {
        }
        
        public FibonacciSequenceResponse(int start, int count, long[] sequence) {
            this.start = start;
            this.count = count;
            this.sequence = sequence;
            this.error = null;
        }
        
        public FibonacciSequenceResponse(String error) {
            this.start = -1;
            this.count = -1;
            this.sequence = new long[0];
            this.error = error;
        }
        
        public int getStart() {
            return start;
        }
        
        public void setStart(int start) {
            this.start = start;
        }
        
        public int getCount() {
            return count;
        }
        
        public void setCount(int count) {
            this.count = count;
        }
        
        public long[] getSequence() {
            return sequence;
        }
        
        public void setSequence(long[] sequence) {
            this.sequence = sequence;
        }
        
        public String getError() {
            return error;
        }
        
        public void setError(String error) {
            this.error = error;
        }
    }
}
