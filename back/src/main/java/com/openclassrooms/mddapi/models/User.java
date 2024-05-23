package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * Classe représentant l'entité User dans la base de données.
 * Cette classe est utilisée pour mapper les données de la table USERS dans la base de données.
 * 
 * @author Didier Barriere Doleac
 * @version 1.0
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
   * Identifiant unique de l'utilisateur.
   * Généré automatiquement par la base de données.
   */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
   * Adresse e-mail de l'utilisateur.
   * Ne peut pas être null et doit être une adresse e-mail valide.
   * Doit être unique dans la base de données.
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
    @Column(name = "username")
    private String userName;

    /**
   * Mot de passe de l'utilisateur.
   * Ne peut pas être null et doit avoir une longueur maximale de 50 caractères.
   */
    @NonNull
    @Size(max = 255)
    private String password;

}