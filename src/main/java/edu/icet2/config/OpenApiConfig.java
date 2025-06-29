package edu.icet2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration for OpenAPI documentation.
 * Provides comprehensive API documentation with Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Local Development Server");

        return new OpenAPI()
                .servers(List.of(localServer))
                .info(new Info()
                        .title("Student Management System API")
                        .description("A comprehensive REST API for managing student records with full CRUD operations, " +
                                   "search capabilities, and statistical reporting.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Student Management Team")
                                .email("support@studentmanagement.edu")
                                .url("https://github.com/your-org/student-management"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}