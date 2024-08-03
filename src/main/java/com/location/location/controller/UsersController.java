package com.location.location.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.location.location.DTO.LoginResponseDto;
import com.location.location.DTO.UsersDto;
import com.location.location.model.Users;
import com.location.location.service.UsersService;

@RestController
@RequestMapping("/api/user")
public class UsersController {
	
    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PutMapping(value="/{id}")
    public UsersDto updateUser(@PathVariable("id") final Long id, @RequestBody UsersDto u,UserDetails userDetails) {
        Optional<UsersDto> user = usersService.getUserById(id);
        if (user.isPresent()) {
            return usersService.save(u);
        } else {
            return null;
        }
    }

    @GetMapping(value="")
    public Iterable<UsersDto> getUsers() {
        return this.usersService.getAllUsers();
    }

    @GetMapping(value="/{id}")
    public Optional<UsersDto> getUserById(@PathVariable("id") long id) {
        return this.usersService.getUserById(id);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable("id") long id) {
        this.usersService.deleteUserById(id);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping(value="/user/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable Long id) {
        try {
            UsersDto userDto = usersService.findById(id);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
