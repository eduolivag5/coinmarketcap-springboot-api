package com.example.coinmarketcap.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

    /**
     * Configuramos el RestTemplate manualmente para evitar el error de dependencia
     * y establecer los Timeouts que Render necesita para no dar 502.
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 10 segundos de espera para conectar
        factory.setConnectTimeout(10000);
        // 10 segundos de espera para leer datos
        factory.setReadTimeout(10000);
        return new RestTemplate(factory);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Crypto Dashboard API")
                        .version("1.0.0")
                        .description("API profesional para el seguimiento de criptoactivos en tiempo real. " +
                                "Conecta con los datos oficiales de CoinMarketCap.")
                        .contact(new Contact()
                                .name("Eduardo Oliva Garcia")
                                .url("https://eduardo-oliva.netlify.app/")
                                .email("eduolivag5@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization");
            }
        };
    }
}