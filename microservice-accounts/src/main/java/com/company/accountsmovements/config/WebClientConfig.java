package com.company.accountsmovements.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuración centralizada para el WebClient.
 * Define un bean de WebClient que puede ser inyectado en cualquier servicio.
 *
 * @autor Christian Paul Salinas
 * @version 1.0
 */

@Configuration
public class WebClientConfig {

    @Value("${client-service.base-url}")
    private String clientServiceBaseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(clientServiceBaseUrl)
                .build();
    }
}
