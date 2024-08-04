package com.location.location;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	/**
	 * Configure les règles CORS (Cross-Origin Resource Sharing) pour l'application.
	 *
	 * Cette méthode crée un `WebMvcConfigurer` personnalisé pour configurer les
	 * règles CORS. Elle permet aux requêtes provenant de `http://localhost:4200`
	 * d'accéder aux endpoints situés sous `/api/**` de l'application. Elle autorise
	 * plusieurs méthodes HTTP (`GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`), accepte
	 * tous les en-têtes, et permet d'envoyer des cookies avec les requêtes.
	 *
	 * @return une instance configurée de `WebMvcConfigurer`
	 */
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

}
