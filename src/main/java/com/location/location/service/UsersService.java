package com.location.location.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.location.model.Users;
import com.location.location.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	public Iterable<Users> getAllUsers() {
		return usersRepository.findAll();
	}
	
	public Optional<Users> getUserById(long id) {
		return usersRepository.findById(id);
	}
	
	public void deleteUserById(long id) {
	 usersRepository.deleteById(id);
	}	
	
	public Users save(Users user) {
		return usersRepository.save(user);
	}

}
