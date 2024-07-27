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
	 * Cette classe permet d'associer notre utilisateur en base de données à l'utilisateur de spring security
	 * Si le user est present en base de données, spring security fait appel au contexte afin de lui donner la permission
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
            getGrantedAuthorities(user.getRole()) // Ajout des rôles ici
        );
    }

    /**
     * Cette methode permet de donner ax=cces a certaines requete a l'aide d'un role
     * @param role
     * @return
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

}
