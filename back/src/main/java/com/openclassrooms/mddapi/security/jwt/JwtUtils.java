package com.openclassrooms.mddapi.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;

/**
 * Classe utilitaire pour travailler avec JSON Web Tokens (JWT).
 * <p>
 * Cette classe fournit des méthodes pour générer un JWT, obtenir l'email d'un utilisateur à partir d'un JWT, et valider un JWT.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Génère un JWT pour un utilisateur authentifié.
     *
     * @param authentication L'objet Authentication représentant l'utilisateur authentifié.
     * @return Un JWT représentant l'utilisateur authentifié.
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    /**
     * Obtient l'email d'un utilisateur à partir d'un JWT.
     *
     * @param token Le JWT à analyser.
     * @return L'email de l'utilisateur.
     */
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Valide un JWT.
     *
     * @param authToken Le JWT à valider.
     * @return true si le JWT est valide, false sinon.
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