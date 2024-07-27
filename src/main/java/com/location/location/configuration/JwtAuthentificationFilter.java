

package com.location.location.configuration;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.location.location.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthentificationFilter extends OncePerRequestFilter {

	@Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;
    
    /**
	 * OncePerRequestFilter garantie une seule execution par requete, Verifie si le header est present est commence par Bearer
	 * Extrait le token sans le "Bearer ", assigner la valeur a la variable jwt
	 * Associer le user au token et donner la permission à l'utilisateur.
	 * retourne la requet aisni que la reponse.
	 * 
	 */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Check if Authorization header is present and starts with Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Extract the token without "Bearer "
        } else if (authHeader != null) {
            jwt = authHeader; // Handle the case where Bearer is not present
        }

        if (jwt != null) {
            try {
                username = jwtService.extractUsername(jwt);
            } catch (Exception e) {
                logger.warn("Unable to extract JWT token");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}