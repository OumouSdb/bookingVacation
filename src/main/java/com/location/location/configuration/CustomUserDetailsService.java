package com.location.location.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.location.location.model.Users;
import com.location.location.repository.UsersRepository;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

	
    @Autowired
    private UsersRepository usersRepository;

    
    /**
	 * Charge les détails de l'utilisateur en fonction de son email depuis la base de données et les transforme en un objet UserDetails que Spring Security
	 * utilise pour authentifier et autoriser l'utilisateur.
	 * Si les informations de l'utilisateur sont correctes, Spring Security crée une session d'authentification pour l'utilisateur.
	 */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            getGrantedAuthorities(user.getRole())
        );
    }

    /**
     * Utilisée pour transformer les rôles de l'utilisateur en autorités reconnues par Spring Security. 
     * Ces autorités sont ensuite utilisées par Spring Security pour autoriser l'accès de l'utilisateur aux différentes
     * parties de l'application en fonction de ses rôles.
     * @param role
     * @return
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
