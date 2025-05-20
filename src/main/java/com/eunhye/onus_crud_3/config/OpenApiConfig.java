package com.eunhye.onus_crud_3.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

public class OpenApiConfig {

    @Value("${eunhye.openapi.dev-url}")
    private String devUrl;

    @Value("${eunhye.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.url(devUrl);
        devServer.description("Server URL for development");

        Server prodServer = new Server();
        prodServer.url(prodUrl);
        prodServer.description("Server URL for production");

        Contact contact = new Contact();
        contact.setName("Eunhye");
        contact.setEmail("tkrhd0210@gmail.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Employee Management API")
                .version("1.0")
                .contact(contact)
                .description("This is a sample API for managing employees.")
                .termsOfService("http://swagger.io/terms/")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
