package com.location.location.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@EnableWebSecurity
public class SwaggerConfig{
	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info().title("location")
	                        .description("location api")
	                        .version("1.0.0"))
	                .components(new Components().addSecuritySchemes("JWT", securityScheme()))
	                .addSecurityItem(new SecurityRequirement().addList("JWT"));
	    }

	    @Bean
	    public SecurityScheme securityScheme() {
	        return new SecurityScheme()
	                .name("JWT")
	                .type(SecurityScheme.Type.HTTP)
	                .scheme("bearer")
	                .bearerFormat("JWT");
	    }
}
