package com.sportecommerce.proyecto.v1.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8080")
                                .description("Local server"),
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://127.0.0.1:8080")
                                .description("Local server"),
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("https://tucompra.com")
                                .description("Production server")
                ))
                .info(new Info()
                        .title("Order Service API")
                        .description("Order Service API Description")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Codmind")
                                .url("https://codmind.com")
                                .email("apis@codmind.com")
                        )
                );
    }
}

