package com.location.location.configuration;

import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Value("${security.jwt.secret-key}")
	private String jwtKey;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtAuthentificationFilter jwtAuthenticationFilter;

	/**
	 * Désactive la protection CSRF car l'application utilise des jetons JWT qui ne
	 * nécessitent pas cette protection. Permet l'accès public (sans
	 * authentification) aux URL spécifiées. Exige une authentification pour toutes
	 * les autres requêtes. Ajoute un filtre personnalisé (jwtAuthenticationFilter)
	 * avant le filtre UsernamePasswordAuthenticationFilter pour traiter les jetons
	 * JWT.
	 * 
	 * @param http
	 * @return securityFilterChain
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeRequests(authorize -> authorize
						.requestMatchers("/static/**", "**.jpg", "**.png", "**.jpeg", "**.JPG").permitAll()
						.requestMatchers("/api/auth/login", "/api/auth/register", "/v3/api-docs/**", "/swagger-ui/**",
								"/swagger-ui.html")
						.permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	/**
	 * BCrypt est un algorithme de hachage de mot de passe sécurisé.
	 * 
	 * @return
	 */
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	/**
	 * Utilise une clé secrète pour créer un JwtDecoder avec l'algorithme de hachage
	 * HmacSHA256. Le JwtDecoder est utilisé pour décoder et vérifier les jetons
	 * JWT.
	 * 
	 * @return
	 */
	JwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(), "HmacSHA256");
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	@Bean
	/**
	 * Utilise une clé secrète pour créer un JwtEncoder. Le JwtEncoder est utilisé
	 * pour encoder et signer les jetons JWT
	 * 
	 * @return
	 */
	JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
	}

	@Bean
	/**
	 * Crée un `AuthenticationManager` pour gérer l'authentification.
	 *
	 * Cette méthode configure un `AuthenticationManager` en utilisant un
	 * `AuthenticationManagerBuilder`. Le `AuthenticationManager` est configuré avec
	 * un service qui charge les utilisateurs (`userDetailsService`) et un encodeur
	 * de mots de passe (`passwordEncoder`).
	 *
	 * @param http un objet `HttpSecurity` utilisé pour configurer la sécurité
	 * @return une instance configurée de `AuthenticationManager`
	 * @throws Exception si une erreur se produit pendant la configuration
	 */
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return builder.build();
	}

	@Bean
	/**
	 * Crée un `AuthenticationProvider` pour gérer la logique d'authentification.
	 *
	 * Cette méthode retourne un `DaoAuthenticationProvider` configuré avec un
	 * service de chargement d'utilisateurs (`userDetailsService`) et un encodeur de
	 * mots de passe (`passwordEncoder`).
	 *
	 * @return une instance configurée de `AuthenticationProvider`
	 */
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}
