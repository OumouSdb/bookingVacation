package com.location.location;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:4200")
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                        .allowedHeaders("*")
	                        .allowCredentials(true);
	            }
	        };
	    }

//	  @ControllerAdvice
//	  public class GlobalExceptionHandler {
//
//	      @ExceptionHandler(MethodArgumentNotValidException.class)
//	      public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//	          String errorMessage = ex.getBindingResult().getFieldErrors().stream()
//	                  .map(DefaultMessageSourceResolvable::getDefaultMessage)
//	                  .collect(Collectors.joining(", "));
//	          return ResponseEntity.badRequest().body(errorMessage);
//	      }
//	  }
	  


}
