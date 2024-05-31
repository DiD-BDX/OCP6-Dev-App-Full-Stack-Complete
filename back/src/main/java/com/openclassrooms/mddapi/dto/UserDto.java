package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Classe représentant un objet de transfert de données (DTO) pour un utilisateur.
 * Cette classe est utilisée pour transférer des données entre le client et le serveur.
 * 
 * @author Didier Barriere Doleac
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * Identifiant unique de l'utilisateur.
     */
    private Long id;

    /**
     * Adresse e-mail de l'utilisateur.
     * Ne peut pas être null et doit être une adresse e-mail valide.
     */
    @NonNull
    @Size(max = 100)
    @Email
    private String email;

    /**
     * Nom d'utilisateur.
     * Ne peut pas être null et doit avoir une longueur maximale de 50 caractères.
     */
    @NonNull
    @Size(max = 50)
    private String username;

    /**
     * Mot de passe de l'utilisateur.
     * Ignoré lors de la sérialisation JSON et doit avoir une longueur maximale de 50 caractères.
     */
    @JsonIgnore
    @Size(max = 255)
    private String password;
}