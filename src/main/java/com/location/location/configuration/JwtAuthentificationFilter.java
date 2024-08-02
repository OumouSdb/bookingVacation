

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
	  * OncePerRequestFilter est une classe utilitaire fournie par Spring Security.
	  * Garantit que la méthode de filtrage (doFilterInternal) est exécutée une seule fois par requête HTTP exemple (JWT)
	  * 
	 */

    
    /**
     * utilisée pour implémenter la logique de filtrage personnalisée pour l'authentification des utilisateurs.
     * Le filtre vérifie les en-têtes de la requête pour un jeton JWT, extrait les informations d'utilisateur
     * Valide le jeton et configure le contexte de sécurité de Spring Security.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        } else if (authHeader != null) {
            jwt = authHeader;
        }

        if (jwt != null) {
            try {
                username = jwtService.extractUsername(jwt);
            } catch (Exception e) {
                logger.warn("Unable to extract JWT token", e);
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