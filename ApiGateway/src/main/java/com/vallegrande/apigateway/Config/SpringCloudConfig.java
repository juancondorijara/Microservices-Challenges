package com.vallegrande.apigateway.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder)
    {
        return routeLocatorBuilder.routes()
                .route("nombre", rt -> rt.path("/nombre/**")
                        .uri("http://localhost:8081/"))
                .route("vendorModule", rt -> rt.path("/vendor/**")
                        .uri("http://localhost:8082/"))
                .build();

    }
}
