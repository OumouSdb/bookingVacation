package com.location.location.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.location.location.model.Users;
import com.location.location.repository.UsersRepository;

@Service
public class LoginService implements UserDetailsService{
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 Users user = usersRepository.findByEmail(email);
		    if (user == null) {
		        throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
		    }

		    List<GrantedAuthority> authorities = getGrantedAuthorities(user.getRole());
		    return new User(user.getEmail(), user.getPassword(), authorities);

			}

	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}



}
