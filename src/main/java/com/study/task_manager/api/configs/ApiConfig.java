package com.study.task_manager.api.configs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Value("${api.base-url}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}