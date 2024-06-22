package com.openclassrooms.mddapi.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

/**
 * {@link JwtValidator} est une classe utilitaire pour valider un JSON Web Token (JWT).
 * <p>
 * Cette classe fournit une méthode pour valider un JWT.
 * 
 * @see Component
 * @see Logger
 * @see LoggerFactory
 * @see Value
 * @see Jwts
 * @see SignatureException
 * @see MalformedJwtException
 * @see ExpiredJwtException
 * @see UnsupportedJwtException
 * @see IllegalArgumentException
 */
@Component
public class JwtValidator {
    private static final Logger logger = LoggerFactory.getLogger(JwtValidator.class);

    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    /**
     * Valide un JWT.
     *
     * @param authToken Le JWT à valider.
     * @return true si le JWT est valide, false sinon.
     * 
     * @see Jwts
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}