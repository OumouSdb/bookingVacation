package com.location.location.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.location.location.DTO.UsersDto;
import com.location.location.model.Users;
import com.location.location.service.JWTService;
import com.location.location.service.UsersService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;
    
	
	@PostMapping("/register")
	public UsersDto saveUser(@RequestBody UsersDto u) {
		
		return this.usersService.save(u);
		
	}

    @PostMapping("/login")
    public String getToken(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticate(email, password);
            if (authentication != null && authentication.isAuthenticated()) {
                return jwtService.generateToken(authentication);
            }
        } catch (AuthenticationException e) {
            return "Invalid credentials: " + e.getMessage();
        }
        return "Invalid credentials";
    }

    private Authentication authenticate(String email, String password) {
        Users user = usersService.checkLogin(email);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(email, password);
            return authenticationManager.authenticate(authToken);
        }
        return null;
    }

    @PostMapping("/checkLogin")
    public Users checkLogin(@RequestParam String email) {
        return usersService.checkLogin(email);
    }
    
    @GetMapping("/me")
    public UsersDto getCurrentUser(@AuthenticationPrincipal Users user) {
        return usersService.getCurrentUser(user);
    }
}
