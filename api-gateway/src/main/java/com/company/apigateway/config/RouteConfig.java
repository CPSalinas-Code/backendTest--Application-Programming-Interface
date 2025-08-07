package com.company.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *  Clase que define las rutas a donde se va a dirigir el apiGateway
 *
 * @author Christian Paul Salinas
 * @version 1.0
 */
@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("clients-app", r -> r.path("/api/clientes/**", "/api/personas/**")
                        .uri("http://clients-app:8080"))
                .route("accounts-app", r -> r.path("/api/accounts/**", "/api/movements/**", "/api/reports/**")
                        .uri("http://accounts-app:8081"))
                .build();
    }
}
