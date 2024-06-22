package com.openclassrooms.mddapi.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.mddapi.security.services.UserDetailsServiceImpl;

/**
 * {@link AuthTokenFilter} est un filtre qui est exécuté une fois par requête pour l'authentification JWT.
 * Il extrait le token JWT de l'en-tête Authorization de la requête HTTP, valide le token, charge les détails de l'utilisateur associé et définit l'authentification.
 * 
 * @see OncePerRequestFilter
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtValidator jwtValidator;

    @Autowired
    private JwtExtractor jwtExtractor;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    
    /**
     * Cette méthode est appelée pour chaque requête HTTP pour effectuer l'authentification.
     * Elle extrait le token JWT de l'en-tête Authorization, valide le token, charge les détails de l'utilisateur associé et définit l'authentification.
     *
     * @param request la requête HTTP
     * @param response la réponse HTTP
     * @param filterChain le chaîne de filtres
     * @throws ServletException si la requête ne peut pas être traitée
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'écriture de la réponse
     * 
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see FilterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtValidator.validateJwtToken(jwt)) {
                String email = jwtExtractor.getEmailFromJwtToken(jwt);
    
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    
        filterChain.doFilter(request, response);
    }
    
    /**
     * Cette méthode extrait le token JWT de l'en-tête Authorization de la requête HTTP.
     *
     * @param request la requête HTTP
     * @return le token JWT ou null si l'en-tête Authorization est vide ou ne commence pas par "Bearer "
     * 
     * @see HttpServletRequest
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
    
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
    
        return null;
    }
}