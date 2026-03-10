package com.bylvin.langlearnbot.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class AppDocumentation {
	@Bean
	@Primary
	OpenAPI appOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Langlearn-bot")
						.description("Dokumentasi aplikasi chatbot telegram Langlearn-bot")
						.version("v0.0.1-SNAPSHOT")
						.contact(new Contact()
								.name("Team AI Dev")
								.email("carolus.raditya@bankmega.com")))
				.servers(List.of(new Server().url("http://localhost:8007").description("Local Server"),
						new Server().url("http://10.190.7.21:8007").description("CDS Dev Server")))
				.components(new Components()
						.addSecuritySchemes("Authorization", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")))
				.security(List.of(
						new SecurityRequirement()
						.addList("Authorization")));
	}
}
