package com.example;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for App
 */
public class AppTest {
    
    @Test
    public void shouldReturnCorrectGreeting() {
        App app = new App();
        
        // Using AssertJ for assertions as specified in custom instructions
        assertThat(app.getGreeting())
            .isNotNull()
            .isEqualTo("Hello World!");
    }
}
