package io.limeup.flexbets.sport.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${swagger.mock-api-url}")
    private String swaggerMockApiUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .addServersItem(new Server()
                        .url(swaggerMockApiUrl)
                        .description("Mocked API Server"))
                .info(new Info()
                        .title("FlexBets Sport API Documentation")
                        .version("1.0")
                        .description("API documentation for the FlexBets Sport system"));
    }
}

