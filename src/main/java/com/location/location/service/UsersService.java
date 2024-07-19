package com.location.location.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Iterable<UsersDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map((user) -> modelMapper.map(user, UsersDto.class))
                .collect(Collectors.toList());
    }

    public Optional<UsersDto> getUserById(long id) {
        Optional<Users> users = usersRepository.findById(id);
        if (users.isPresent()) {
            return Optional.of(modelMapper.map(users.get(), UsersDto.class));
        } else {
            return Optional.empty();
        }
    }

    public void deleteUserById(long id) {
        usersRepository.deleteById(id);
    }

    public UsersDto save(UsersDto uDto) {
        uDto.setPassword(passwordEncoder.encode(uDto.getPassword()));  // Encode password before saving
        Users u = modelMapper.map(uDto, Users.class);
        Users savedUser = usersRepository.save(u);
        return modelMapper.map(savedUser, UsersDto.class);
    }

    public Users checkLogin(String email) {
        Users u = usersRepository.findByEmail(email);
        if (u != null) {
            System.out.println("email en base de donnée");
            return u;
        }
        return null;
    }

    public void updatePasswords() {
        List<Users> users = usersRepository.findAll();
        for (Users user : users) {
            if (!user.getPassword().startsWith("$2a$")) { // Vérifie si le mot de passe n'est pas encodé en BCrypt
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                usersRepository.save(user);
            }
        }
    }

    public UsersDto getCurrentUser(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UsersDto(user.getId(), user.getName(), user.getEmail(), user.getCreated_at(), user.getUpdated_at());
    }
}
