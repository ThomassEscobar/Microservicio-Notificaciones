package com.consultoria.notificaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Notificaciones - Consultoría")
                        .version("1.0.0")
                        .description("Microservicio encargado de la bitácora, envío y depuración de alertas del sistema.")
                        .contact(new Contact()
                                .name("Soporte de Infraestructura")
                                .email("soporte.infra@consultoria.com")));
    }
}