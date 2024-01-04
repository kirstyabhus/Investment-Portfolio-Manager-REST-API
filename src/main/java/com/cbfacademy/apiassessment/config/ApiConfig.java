package com.cbfacademy.apiassessment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {

    @Value("${alphavantage.baseurl}")
    private String baseUrl;

    @Bean
    public String apiKey() {
        return System.getenv("ALPHAVANTAGE_API_KEY_2");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
