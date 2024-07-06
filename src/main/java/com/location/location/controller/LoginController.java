package com.location.location.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.location.location.service.JWTService;



@RestController
@RequestMapping("/")
public class LoginController {


	private JWTService jwtService;
	
	public LoginController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping("/auth/login")
	public String getToken(Authentication authentication) {
        		String token = jwtService.generateToken(authentication);
        		return token;
	}
	
}
