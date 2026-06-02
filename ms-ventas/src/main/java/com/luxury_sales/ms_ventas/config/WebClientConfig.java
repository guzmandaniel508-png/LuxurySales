package com.luxury_sales.ms_ventas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.urls.ms-producto}")
    private String urlProducto;

    @Value("${app.urls.ms-usuario}")
    private String urlUsuario;

    @Value("${app.urls.ms-pagos}")
    private String urlPagos;

    @Bean
    public WebClient productoWebClient() {
        return WebClient.builder().baseUrl(urlProducto).build();
    }

    @Bean
    public WebClient usuarioWebClient() {
        return WebClient.builder().baseUrl(urlUsuario).build();
    }

    @Bean
    public WebClient pagosWebClient() {
        return WebClient.builder().baseUrl(urlPagos).build();
    }
}