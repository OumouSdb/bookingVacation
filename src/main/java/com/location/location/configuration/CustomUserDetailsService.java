//package com.location.location.configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.location.location.model.Users;
//import com.location.location.repository.UsersRepository;
//
//
//@Service
//public abstract class CustomUserDetailsService implements UserDetailsService{
//	
//	@Autowired
//	private UsersRepository userRepository;
//	
////	public UserDetails loadUserByUserName(String email) throws UsernameNotFoundException{
////		
////		Users user = userRepository.findByEmail(email);
////		
////		return new Users(user.getName(), user.getPassword(), getGrantedAutorities(user.getRole()));
////
////	}
//	
//	private List<GrantedAuthority> getGrantedAutorities(String role){
//		List<GrantedAuthority> autorities = new ArrayList<GrantedAuthority>();
//		autorities.add(new SimpleGrantedAuthority("ROLE_"+role));
//		return autorities;
//	}
//	
//	
//
//}
