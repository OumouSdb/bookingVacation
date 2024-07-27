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

	    @SuppressWarnings("deprecation")
	    @Bean
	    
	    /**
	     * Ne met pas de filtres sur certaines endPoint ainsi que sur swagger
	     * met un filtre sur les Session sans etat
	     * authetication provider ?
	     * addFilterBefore ?
	     * http build ?
	     * @param http
	     * @return
	     * @throws Exception
	     */
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeRequests(authorize -> authorize
	                .requestMatchers("/api/auth/**", "/images/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
	                .anyRequest().authenticated()
	            )
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .authenticationProvider(authenticationProvider())
	            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

	    @Bean
	   /**
	    * Utilise Bcrypte pour encode le mot de passe ?
	    * @return
	    */
	    BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    /**
	     * Decode le token avec l'algotythme HmacSHA256 ?
	     * @return
	     */
	    JwtDecoder jwtDecoder() {
	        SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(), "HmacSHA256");
	        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	    }

	    @Bean
	    /**
	     * Encode le token avec l'algorythm becrypte
	     * @return
	     */
	     JwtEncoder jwtEncoder() {
	        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
	    }

	    @Bean
	    /**
	     *  ???
	     * @param http
	     * @return
	     * @throws Exception
	     */
	     AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
	        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	        return builder.build();
	    }

	    @Bean
	    /**
	     *  ???
	     * @return
	     */
	     AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }
}
