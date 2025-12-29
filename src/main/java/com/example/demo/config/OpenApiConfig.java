package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme jwtScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("Multi-Branch Academic Calendar Harmonizer")
                        .description("API for managing academic calendars across multiple branches")
                        .version("1.0.0")
                )
                .servers(List.of(
                        new Server().url("https://9049.408procr.amypo.ai/")
                ))
                .addSecurityItem(
                        new SecurityRequirement().addList("BearerAuth")
                )
                .components(
                        new Components().addSecuritySchemes(
                                "BearerAuth", jwtScheme
                        )
                );
    }
}
