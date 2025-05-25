package com.onus.crud_project_review.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class OpenApiConfig {
    @Value("${fiction2st.openapi.dev-url}")
    private String devUrl;

    @Value("${fiction2st.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.url(devUrl);
        devServer.description("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.url(prodUrl);
        prodServer.description("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("fakerfan90@gmail.com");
        contact.setName("faker");
        contact.url("https://www.fiction2st.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Product Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials.")
                .termsOfService("https://www.teddykwak.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
