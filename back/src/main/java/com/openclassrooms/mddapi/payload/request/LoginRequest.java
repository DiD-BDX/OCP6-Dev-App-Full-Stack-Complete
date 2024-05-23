package com.openclassrooms.mddapi.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe représentant une demande de connexion.
 * <p>
 * Cette classe est utilisée pour transporter les données de connexion d'un utilisateur, 
 * c'est-à-dire son email et son mot de passe.
 */
public class LoginRequest {
    
    /**
     * L'email de l'utilisateur.
     * <p>
     * Ce champ est obligatoire et ne peut pas être vide.
     */
    @NotBlank
    private String email;

    /**
     * Le mot de passe de l'utilisateur.
     * <p>
     * Ce champ est obligatoire et ne peut pas être vide.
     */
    @NotBlank
    private String password;

    /**
     * Récupère l'email de l'utilisateur.
     *
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de l'utilisateur.
     *
     * @param email L'email de l'utilisateur.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Récupère le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param password Le mot de passe de l'utilisateur.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}