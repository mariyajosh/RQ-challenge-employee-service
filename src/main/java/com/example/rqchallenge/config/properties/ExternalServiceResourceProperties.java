package com.example.rqchallenge.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "api.external-service")
@Configuration
@Setter
@Getter
public class ExternalServiceResourceProperties {
    private String baseUrl;
    private String employees;
    private String employeeById;
    private String createEmployee;
    private String deleteEmployee;
    private Integer retryDelay;
    private Integer maxRetryAttempts;
}
