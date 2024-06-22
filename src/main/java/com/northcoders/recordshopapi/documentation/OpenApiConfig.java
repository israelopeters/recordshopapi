package com.northcoders.recordshopapi.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI defineOpenApi () {
        Server server = new Server();
        server.setDescription("Cloud Production");

        Contact myContact = new Contact();
        myContact.setName("Israel Peters");
        myContact.setEmail("israelopeters@gmail.com");

        Info information = new Info()
                .title("The Record Shop API")
                .version("1.0")
                .description("This is the API for The Record Shop, which allows a user to get all albums in stock. " +
                        "The user can also get an album by its ID or a list of albums by a given artist, genre, or release year. " +
                        "Finally, the user can add a new album, update or delete an existing album, or get information about " +
                        "a specific album by its name. ")
                .contact(myContact);

        return new OpenAPI().info(information).servers(List.of(server));
    }
}
