package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * La classe UserDto est un objet de transfert de données (DTO) pour le transfert de données de User.
 * Elle est utilisée pour transférer des données entre les couches de l'application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * L'identifiant unique de l'utilisateur.
     * Il correspond à l'identifiant de l'utilisateur dans la base de données.
     */
    private Long id;

    /**
     * L'adresse e-mail de l'utilisateur.
     * Ce champ est obligatoire, doit être une adresse e-mail valide et ne peut pas dépasser 100 caractères.
     */
    @NonNull
    @Size(max = 100)
    @Email
    private String email;

    /**
     * Le nom d'utilisateur.
     * Ce champ est obligatoire et ne peut pas dépasser 50 caractères.
     */
    @NonNull
    @Size(max = 50)
    private String username;

    /**
     * Le mot de passe de l'utilisateur.
     * Ce champ est ignoré lors de la sérialisation JSON et ne peut pas dépasser 255 caractères.
     */
    @JsonIgnore
    @Size(max = 255)
    private String password;
}