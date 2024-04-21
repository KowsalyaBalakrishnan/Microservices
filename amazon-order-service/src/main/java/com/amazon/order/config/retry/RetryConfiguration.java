package com.amazon.order.config.retry;

/*import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.time.Duration;

@Configuration
public class RetryConfiguration {

    @Autowired
    private RetryRegistry retryRegistry;

    @Bean
    public Retry retryConfiguration() {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofSeconds(3))
                .retryExceptions(RestClientException.class, HttpServerErrorException.class, ResourceAccessException.class)
                .build();
        return retryRegistry.retry("retryMechanism", retryConfig);
    }

}*/
