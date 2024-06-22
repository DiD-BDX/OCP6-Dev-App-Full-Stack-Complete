package com.openclassrooms.mddapi.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe représentant une demande de connexion.
 * <p>
 * Cette classe est utilisée pour transporter les données de connexion d'un utilisateur, 
 * c'est-à-dire son email, son nom d'utilisateur et son mot de passe.
 * Elle utilise l'annotation {@link NotBlank} pour indiquer que les champs ne peuvent pas être vides.
 * 
 * @see NotBlank
 */
public class LoginRequest {
    
    /**
     * L'email de l'utilisateur.
     * <p>
     * Ce champ est obligatoire et ne peut pas être vide.
     * Il utilise l'annotation {@link NotBlank}.
     * 
     * @see NotBlank
     */
    @NotBlank
    private String email;

    /**
     * Le nom d'utilisateur de l'utilisateur.
     * <p>
     * Ce champ est obligatoire et ne peut pas être vide.
     * Il utilise l'annotation {@link NotBlank}.
     * 
     * @see NotBlank
     */
    @NotBlank
    private String username;

    /**
     * Le mot de passe de l'utilisateur.
     * <p>
     * Ce champ est obligatoire et ne peut pas être vide.
     * Il utilise l'annotation {@link NotBlank}.
     * 
     * @see NotBlank
     */
    @NotBlank
    private String password;

    /**
     * Récupère l'email ou le nom d'utilisateur de l'utilisateur.
     * <p>
     * Cette méthode renvoie l'email si il est non null, sinon elle renvoie le nom d'utilisateur.
     *
     * @return L'email ou le nom d'utilisateur de l'utilisateur.
     */
    public String getEmailOrUsername() {
        return email != null ? email : username;
    }

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
     * Récupère le nom d'utilisateur de l'utilisateur.
     *
     * @return Le nom d'utilisateur de l'utilisateur.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur de l'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur.
     */
    public void setUsername(String username) {
        this.username = username;
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