package cl.esperanza.reporte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient pagoWebClient(@Value("${pago.service.url:http://localhost:8084/api/v1/pago}") String pagoServiceUrl) {
        return WebClient.builder().baseUrl(pagoServiceUrl).build();
    }

    @Bean
    public WebClient facturacionWebClient(@Value("${facturacion.service.url:http://localhost:8083/api/v1/facturacion}") String facturacionServiceUrl) {
        return WebClient.builder().baseUrl(facturacionServiceUrl).build();
    }

    @Bean
    public WebClient reparacionWebClient(@Value("${reparacion.service.url:http://localhost:8081/api/v1/reparaciones}") String reparacionesServiceUrl) {
        return WebClient.builder().baseUrl(reparacionesServiceUrl).build();
    }
}
