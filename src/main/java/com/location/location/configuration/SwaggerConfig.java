package com.location.location.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@EnableWebSecurity
@Configuration
public class SwaggerConfig {

	@Bean
	/**
	 * Configure et retourne une instance personnalisée de `OpenAPI`.
	 *
	 * Cette méthode crée une instance d'`OpenAPI` pour la documentation de l'API.
	 * Elle définit le titre, la description et la version de l'API. Elle ajoute
	 * aussi un schéma de sécurité basé sur JWT pour protéger les endpoints de
	 * l'API.
	 *
	 * @return une instance configurée de `OpenAPI`
	 */
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("location").description("location api").version("1.0.0"))
				.components(new Components().addSecuritySchemes("JWT", securityScheme()))
				.addSecurityItem(new SecurityRequirement().addList("JWT"));
	}

	@Bean
	/**
	 * Configure et retourne un schéma de sécurité basé sur JWT.
	 *
	 * Cette méthode crée une instance de `SecurityScheme` qui décrit le type de
	 * sécurité utilisé par l'API, en l'occurrence, un schéma HTTP basé sur JWT
	 * (JSON Web Token).
	 *
	 * @return une instance configurée de `SecurityScheme`
	 */
	public SecurityScheme securityScheme() {
		return new SecurityScheme().name("JWT").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
	}
}
