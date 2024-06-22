package com.openclassrooms.mddapi.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;

/**
 * {@link JwtUtils} est une classe utilitaire pour travailler avec JSON Web Tokens (JWT).
 * <p>
 * Cette classe fournit des méthodes pour générer un JWT.
 * 
 * @see Component
 */
@Component
public class JwtUtils {
    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Génère un JWT pour un utilisateur authentifié.
     *
     * @param authentication L'objet {@link Authentication} représentant l'utilisateur authentifié.
     * @return Un JWT représentant l'utilisateur authentifié.
     * 
     * @see Authentication
     * @see UserDetailsImpl
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
}