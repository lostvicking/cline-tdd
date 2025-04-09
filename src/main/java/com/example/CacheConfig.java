package com.example;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Spring's caching mechanism
 */
@Configuration
@EnableCaching
public class CacheConfig {
    
    /**
     * Configure the cache manager to use a simple in-memory cache
     * 
     * @return the cache manager
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("fibonacci", "nextFibonacci");
    }
}
