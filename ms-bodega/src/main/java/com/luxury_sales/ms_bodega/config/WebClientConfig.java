package com.luxury_sales.ms_bodega.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    
    @Value("${app.urls.ms-producto}")
    private String urlMsProducto;

    @Bean
    public WebClient productoWebClient() {
        return WebClient.builder()
                .baseUrl(urlMsProducto) 
                .build();
    }
}