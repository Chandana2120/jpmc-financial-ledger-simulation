package com.jpmc.midascore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean; // Add this
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MidasCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidasCoreApplication.class, args);
    }

    @Bean // This must be inside the class braces
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

} // This closing brace should be at the very bottom