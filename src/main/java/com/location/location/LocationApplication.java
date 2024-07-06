package com.location.location;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtEncoder;
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
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:3001/api")
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                        .allowedHeaders("*")
	                        .allowCredentials(true);
	            }
	        };
	    }

}
