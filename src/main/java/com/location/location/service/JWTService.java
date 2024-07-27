package com.location.location.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

	 @Value("${security.jwt.secret-key}")
	    private String secretKey;

	    @Value("${security.jwt.expiration-time}")
	    private long jwtExpiration;
	    
	    /**
	     *  ?????
	     * @return
	     */

	    private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	    }
	    
	    /**
	     * Gneration du token
	     * @param userDetails
	     * @param userId
	     * @return un token avec le sinformation du user
	     */

	    @SuppressWarnings("deprecation")
		public String generateToken(UserDetails userDetails, Long userId) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("user_id", userId);
	        claims.put("email", userDetails.getUsername());
	        claims.put("roles", userDetails.getAuthorities());

	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(userDetails.getUsername())
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
	                .signWith(SignatureAlgorithm.HS256, getSigningKey())
	                .compact();
	    }
	    
	    /**
	     * Extrait les informations du user
	     * @param token
	     * @return
	     */

	    public String extractUsername(String token) {
	        return extractAllClaims(token).getSubject();
	    }

	    /**
	     *  ???
	     * @param token
	     * @return
	     */
	    public Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

	    /**
	     *  verifie la validité du token
	     * @param token
	     * @param userDetails
	     * @return
	     */
	    public boolean isTokenValid(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	    /**
	     * Verifie la validité du token
	     * @param token
	     * @return
	     */
	    private boolean isTokenExpired(String token) {
	        return extractAllClaims(token).getExpiration().before(new Date());
	    }
}