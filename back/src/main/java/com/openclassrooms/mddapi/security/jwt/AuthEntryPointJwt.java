package com.openclassrooms.mddapi.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * AuthEntryPointJwt est un composant qui implémente AuthenticationEntryPoint pour gérer les exceptions d'authentification.
 * Il est utilisé pour renvoyer une réponse d'erreur lorsque le client tente d'accéder à une ressource protégée sans authentification valide.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Cette méthode est appelée chaque fois qu'une exception est levée en raison d'une authentification non valide.
     * Elle prépare et envoie une réponse avec le code d'erreur HTTP 401, le message d'erreur et le chemin de la requête.
     *
     * @param request la requête HTTP qui a conduit à l'erreur
     * @param response la réponse HTTP à envoyer
     * @param authException l'exception qui a été levée
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'écriture de la réponse
     * @throws ServletException si la requête ne peut pas être traitée
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}