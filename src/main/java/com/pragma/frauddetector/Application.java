package com.pragma.frauddetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
public class Application {
    private final ApplicationProperties applicationProperties;

    public Application(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getCorePoolSize());
        executor.setMaxPoolSize(applicationProperties.getMaxPoolSize());
        executor.setQueueCapacity(applicationProperties.getQueueCapacity());
        executor.setThreadNamePrefix("fraud-detector-");
        executor.initialize();
        return executor;
    }

    private void validateProperties() {
        if (applicationProperties.getCorePoolSize() <= 0) {
            throw new IllegalStateException("Core pool size must be positive");
        }
        if (applicationProperties.getMaxPoolSize() < applicationProperties.getCorePoolSize()) {
            throw new IllegalStateException("Max pool size must be greater than or equal to core pool size");
        }
        if (applicationProperties.getQueueCapacity() <= 0) {
            throw new IllegalStateException("Queue capacity must be positive");
        }
    }
}

record ApplicationProperties(
    int corePoolSize,
    int maxPoolSize,
    int queueCapacity
) {}