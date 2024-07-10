package com.location.location.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.location.location.DTO.UsersDto;
import com.location.location.model.Users;
import com.location.location.service.UsersService;


@RestController
@RequestMapping("/api/user")
public class UsersController {
	
	@Autowired
	UsersService usersService;
	

	@PutMapping("/{id}")
	public UsersDto updateUser(@PathVariable("id") final Long id, @RequestBody UsersDto u) {
		Optional<UsersDto> user = usersService.getUserById(id);
		if(user.isPresent()) {
			
			return usersService.save(u);

		} else {
			return null;
		}
	}
	
	@GetMapping("")
	public Iterable<UsersDto> getUsers() {
		return this.usersService.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public Optional<UsersDto> getUserById(@PathVariable("id") long id) {
		return this.usersService.getUserById(id);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Long> deleteById(@PathVariable("id") long id) {
		this.usersService.deleteUserById(id);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

}
