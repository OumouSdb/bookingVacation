package com.location.location.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        uDto.setCreated_at(new Date());
        Users u = modelMapper.map(uDto, Users.class);
        Users savedUser = usersRepository.save(u);

        // Charger les détails de l'utilisateur par email
        UserDetails userDetails = customUserDetailsService.loadUserByEmail(uDto.getEmail());

        // Générer le jeton JWT pour l'utilisateur enregistré
        String token = jwtService.generateToken(userDetails, savedUser.getId());

        // Mettre à jour l'objet UsersDto avec le jeton
        UsersDto savedUserDto = modelMapper.map(savedUser, UsersDto.class);
        savedUserDto.setToken(token);

        return savedUserDto;
    }

    public ResponseEntity<LoginResponseDto> checkLogin(String email, String password) {
        Users u = usersRepository.findByEmail(email);
        if (u != null && passwordEncoder.matches(password, u.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("user_id", u.getId());
            claims.put("user_fullName", u.getName());

            LoginResponseDto response = new LoginResponseDto();
           // response.setToken(jwtService.doGenerateToken(claims, email));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void updatePasswords() {
        List<Users> users = usersRepository.findAll();
        for (Users user : users) {
            if (!user.getPassword().startsWith("$2a$")) { // Vérifie si le mot de passe n'est pas encodé en BCrypt
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUpdated_at(new Date());
                usersRepository.save(user);
            }
        }
    }

    
    public Optional<Users> findByEmail(String email) {
        return Optional.of(usersRepository.findByEmail(email));
    }

    public LoginResponseDto getCurrentUser(String token) {
        String jwtToken = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(jwtToken);
        Users user = usersRepository.findByEmail(username);

        return new LoginResponseDto(user.getId(), user.getName(), user.getEmail(),user.getCreated_at(), user.getUpdated_at(), token);
    }

    
    public Optional<Users> findById(Long id) {
        return usersRepository.findById(id);
    }


    public LoginResponseDto getCurrentUser(Users user) {
        return new LoginResponseDto(user.getId(), user.getName(), user.getEmail(), user.getCreated_at(), user.getUpdated_at(), null);
    }
}
