package cl.esperanza.reporte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient pagoWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8084/api/v1/pago").build();
    }

    @Bean
    public WebClient facturacionWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8083/api/v1/facturacion").build();
    }

    @Bean
    public WebClient reparacionWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/v1/reparaciones").build();
    }
}