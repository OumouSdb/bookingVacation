package com.location.location.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.location.location.DTO.UsersDto;
import com.location.location.model.Users;
import com.location.location.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
	public Iterable<UsersDto> getAllUsers() {
		
		List<Users> users = usersRepository.findAll();
		return users.stream().map((user)->modelMapper.map(user, UsersDto.class))
				.collect(Collectors.toList());
	}
	
	public Optional<UsersDto> getUserById(long id) {
		Optional<Users> users = usersRepository.findById(id);
		if(users.isPresent()) {
			return Optional.of(modelMapper.map(users, UsersDto.class));
		}else {
			return Optional.empty();
		}
		
	}
	
	public void deleteUserById(long id) {
	 usersRepository.deleteById(id);
	}	
	
	public UsersDto save(UsersDto uDto) {
		Users u = modelMapper.map(uDto, Users.class);
		Users saveUser = usersRepository.save(u);
		u.setPassword(passwordEncoder().encode(u.getPassword()));
		UsersDto saveUserDto = modelMapper.map(saveUser, UsersDto.class);
		return saveUserDto;
	}
	
	public Users checkLogin(String email) {
	    Users u = this.usersRepository.findByEmail(email);
	    if (u != null) {
	        System.out.println("email en base de donnée");
	        return u;
	    }
	    return u;
	}
	
	public void updatePasswords() {
	    List<Users> users = usersRepository.findAll();
	    for (Users user : users) {
	        if (!user.getPassword().startsWith("$2a$")) { // Vérifie si le mot de passe n'est pas encodé en BCrypt
	            user.setPassword(passwordEncoder().encode(user.getPassword()));
	            usersRepository.save(user);
	        }
	    }

	}
}
