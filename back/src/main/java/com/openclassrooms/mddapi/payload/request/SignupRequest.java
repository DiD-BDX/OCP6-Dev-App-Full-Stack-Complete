package com.openclassrooms.mddapi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Classe représentant une demande d'inscription.
 * <p>
 * Cette classe est utilisée pour transporter les données d'inscription d'un utilisateur, 
 * c'est-à-dire son email, son nom d'utilisateur et son mot de passe.
 * Elle utilise les annotations {@link NotBlank}, {@link Size} et {@link Email} pour la validation des données.
 * 
 * @see NotBlank
 * @see Size
 * @see Email
 */
@Data
public class SignupRequest {
    
    /**
     * L'email de l'utilisateur.
     * <p>
     * Ce champ est obligatoire, doit être un email valide et sa longueur ne doit pas dépasser 100 caractères.
     * Il utilise les annotations {@link NotBlank}, {@link Size} et {@link Email}.
     * 
     * @see NotBlank
     * @see Size
     * @see Email
     */
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    /**
     * Le nom d'utilisateur.
     * <p>
     * Ce champ est obligatoire et sa longueur doit être comprise entre 3 et 50 caractères.
     * Il utilise les annotations {@link NotBlank} et {@link Size}.
     * 
     * @see NotBlank
     * @see Size
     */
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    /**
     * Le mot de passe de l'utilisateur.
     * <p>
     * Ce champ est obligatoire et sa longueur doit être comprise entre 8 et 50 caractères.
     * Il utilise les annotations {@link NotBlank} et {@link Size}.
     * 
     * @see NotBlank
     * @see Size
     */
    @NotBlank
    @Size(min = 8, max = 255)
    private String password;
}