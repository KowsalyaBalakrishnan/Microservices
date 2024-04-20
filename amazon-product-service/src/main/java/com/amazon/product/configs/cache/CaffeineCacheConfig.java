package com.amazon.product.configs.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache("allProducts", cacheBuilder());
        return cacheManager;
    }

    private Cache<Object, Object> cacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(25)
                .expireAfterAccess(15, TimeUnit.SECONDS)
                .initialCapacity(10)
                .build();
    }


}
