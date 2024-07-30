package com.location.location.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.location.location.DTO.LoginDto;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping(value="/register")
    public ResponseEntity<UsersDto> saveUser(@RequestBody UsersDto u) {
        UsersDto savedUser = usersService.save(u);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping(value="/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Users user = usersService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtService.generateToken(userDetails, user.getId());
            
            LoginResponseDto response = new LoginResponseDto(user.getId(), user.getName(), user.getEmail(), user.getCreated_at(), user.getUpdated_at(), token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value="/me")
    public ResponseEntity<LoginResponseDto> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            LoginResponseDto currentUser = usersService.getCurrentUser(token);
            return ResponseEntity.ok(currentUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}