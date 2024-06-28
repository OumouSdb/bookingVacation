package com.location.location.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.location.location.service.JWTService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	

	
private JWTService jwtService;
	
	public LoginController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping("/login")
	public String getToken(Authentication authentication) {
        		String token = jwtService.generateToken(authentication);
        		return token;
	}
	
	@PostMapping("/register")
	public String authentication(Authentication authentication) {
        		String token = jwtService.generateToken(authentication);
        		return token;
	}
	
	@GetMapping("/me")
	public String getAuthentication(Authentication authentication) {
        		String token = jwtService.generateToken(authentication);
        		return token;
	}

}
