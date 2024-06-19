package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * La classe User représente un utilisateur dans la base de données.
 * Chaque utilisateur peut être associé à plusieurs posts et commentaires.
 */
@Entity
@Table(name = "USER", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    /**
     * L'identifiant unique de l'utilisateur.
     * C'est la clé primaire dans la table USER.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'adresse e-mail de l'utilisateur.
     * Ce champ est obligatoire, doit être une adresse e-mail valide et unique dans la base de données.
     */
    @NonNull
    @Size(max = 100)
    @Email
    private String email;

    /**
     * Le nom d'utilisateur.
     * Ce champ est obligatoire et limité à 50 caractères.
     */
    @NonNull
    @Size(max = 50)
    @Column(name = "username")
    private String username;

    /**
     * Le mot de passe de l'utilisateur.
     * Ce champ est obligatoire et limité à 255 caractères.
     */
    @NonNull
    @Size(max = 255)
    private String password;
}