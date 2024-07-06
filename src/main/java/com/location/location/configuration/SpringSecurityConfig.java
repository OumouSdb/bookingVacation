package com.location.location.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.location.location.model.Users;
import com.location.location.repository.UsersRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

@Configuration
public class SpringSecurityConfig {
	
	private UsersRepository usersRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
		return http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				.httpBasic(Customizer.withDefaults()).build();
			
	}
	
	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();		
		return new InMemoryUserDetailsManager(user);
	}
	
	public UserDetails loadUserByUsername(String username) {
		Users user = usersRepository.findByEmail(username);
		
		return new User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user.getRole()));
	}

	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private String jwtKey = "e6729deecc572b7fff727104e902ab7b2a98dd79ac1ad7bf562f5f9d29e997e7";
	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length,"RSA");
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}
	
	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
//		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//	authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
//		return authenticationManagerBuilder.build();
//	}

}
