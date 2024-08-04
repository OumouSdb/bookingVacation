package com.location.location.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.catalina.authenticator.SavedRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.location.location.DTO.LoginResponseDto;
import com.location.location.DTO.UsersDto;
import com.location.location.configuration.CustomUserDetailsService;
import com.location.location.model.Users;
import com.location.location.repository.UsersRepository;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	public Iterable<UsersDto> getAllUsers() {
		List<Users> users = usersRepository.findAll();
		return users.stream().map(user -> modelMapper.map(user, UsersDto.class)).collect(Collectors.toList());
	}

	public Optional<UsersDto> getUserById(long id) {
		Optional<Users> users = usersRepository.findById(id);
		return users.map(user -> modelMapper.map(user, UsersDto.class));
	}

	public void deleteUserById(long id) {
		usersRepository.deleteById(id);
	}

	public UsersDto save(UsersDto uDto) {
		uDto.setPassword(passwordEncoder.encode(uDto.getPassword()));
		if (uDto.getRole() == null || uDto.getRole() == "") {
			uDto.setRole("USER");
		}
		Users u = modelMapper.map(uDto, Users.class);
		Users savedUser = usersRepository.save(u);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(uDto.getEmail());
		String token = jwtService.generateToken(userDetails, savedUser.getId());
		savedUser.setToken(token);
		UsersDto savedUserDto = modelMapper.map(savedUser, UsersDto.class);

		return savedUserDto;
	}

	public void updatePasswords() {
		List<Users> users = usersRepository.findAll();
		for (Users user : users) {
			if (!user.getPassword().startsWith("$2a$")) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setUpdated_at(new Date());
				usersRepository.save(user);
			}
		}
	}

	public Optional<UsersDto> findByEmail(String email) {
		Users user = usersRepository.findByEmail(email);
		if (user != null) {
			return Optional.of(modelMapper.map(user, UsersDto.class));
		} else {
			return Optional.empty();
		}
	}

	public LoginResponseDto getCurrentUser(String token) {
		String jwtToken = token.replace("Bearer ", "");
		String username = jwtService.extractUsername(jwtToken);
		Users user = usersRepository.findByEmail(username);

		return new LoginResponseDto(user.getId(), user.getName(), user.getEmail(), user.getCreated_at(),
				user.getUpdated_at(), token);
	}

	public UsersDto findById(Long id) {
		Optional<Users> userOptional = usersRepository.findById(id);

		if (userOptional.isPresent()) {
			return modelMapper.map(userOptional.get(), UsersDto.class);
		}
		return null;
	}

	public LoginResponseDto getCurrentUser(Users user) {
		return new LoginResponseDto(user.getId(), user.getName(), user.getEmail(), user.getCreated_at(),
				user.getUpdated_at(), null);
	}
}
