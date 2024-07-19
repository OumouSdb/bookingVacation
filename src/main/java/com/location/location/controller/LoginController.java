package com.location.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.location.location.DTO.LoginResponseDto;
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
        return usersService.save(u);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> getToken(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticate(email, password);
            if (authentication != null && authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authentication);
                Users user = usersService.checkLogin(email);
                LoginResponseDto response = new LoginResponseDto();
                response.setId(user.getId());
                response.setName(user.getName());
               
                response.setEmail(user.getEmail());
                response.setToken(token);
                return ResponseEntity.ok(response);
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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

//    @PostMapping("/checkLogin")
//    public Users checkLogin(@RequestParam String email) {
//        return usersService.checkLogin(email);
//    }

    @GetMapping("/me")
    public UsersDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }
        String email = authentication.getName(); // Obtenir l'email de l'utilisateur authentifié
        return usersService.getCurrentUser(email);
    }
}
