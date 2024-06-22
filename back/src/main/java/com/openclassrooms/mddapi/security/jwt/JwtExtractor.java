package com.openclassrooms.mddapi.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

/**
 * {@link JwtExtractor} est une classe utilitaire pour extraire des informations d'un JSON Web Token (JWT).
 * <p>
 * Cette classe fournit une méthode pour obtenir l'email d'un utilisateur à partir d'un JWT.
 * 
 * @see Component
 */
@Component
public class JwtExtractor {
    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    /**
     * Obtient l'email d'un utilisateur à partir d'un JWT.
     *
     * @param token Le JWT à analyser.
     * @return L'email de l'utilisateur.
     * 
     * @see Jwts
     */
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}