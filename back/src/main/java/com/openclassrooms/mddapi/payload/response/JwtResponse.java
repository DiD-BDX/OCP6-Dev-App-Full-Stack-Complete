package com.openclassrooms.mddapi.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe représentant la réponse JWT envoyée après l'authentification d'un utilisateur.
 * <p>
 * Cette classe contient le token JWT, le type de token, l'ID de l'utilisateur, le nom d'utilisateur et l'email.
 * Elle est utilisée pour envoyer ces informations à l'utilisateur après une authentification réussie.
 * Elle utilise les annotations {@link Getter} et {@link Setter} de Lombok pour générer automatiquement les getters et setters.
 * 
 * @see Getter
 * @see Setter
 */
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    /**
     * Constructeur pour {@link JwtResponse}.
     * <p>
     * Ce constructeur initialise un nouvel objet {@link JwtResponse} avec le token JWT, l'ID de l'utilisateur, le nom d'utilisateur et l'email.
     * 
     * @param accessToken Le token JWT.
     * @param id L'ID de l'utilisateur.
     * @param username Le nom d'utilisateur.
     * @param email L'email de l'utilisateur.
     * 
     * @see JwtResponse
     */
    public JwtResponse(String accessToken, Long id, String username,String email) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}