package com.openclassrooms.mddapi.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

import jakarta.validation.constraints.Max;

/**
 * La classe PostDto est un objet de transfert de données (DTO) pour le transfert de données de Post.
 * Elle est utilisée pour transférer des données entre les couches de l'application.
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    /**
     * L'identifiant unique du post.
     * Il correspond à l'identifiant du post dans la base de données.
     */
    private Long id;

    /**
     * Le titre du post.
     * Ce champ est obligatoire et ne peut pas dépasser 70 caractères.
     */
    @NonNull
    @Max(70)
    private String title;

    /**
     * Le contenu du post.
     * Ce champ est obligatoire.
     */
    @NonNull
    private String content;

    /**
     * La date de création du post.
     * Elle correspond à la date de création du post dans la base de données.
     */
    private Date createdAt;

    /**
     * La date de dernière mise à jour du post.
     * Elle correspond à la date de dernière mise à jour du post dans la base de données.
     */
    private Date updatedAt;

    /**
     * L'identifiant du sujet associé au post.
     * Il correspond à l'identifiant du sujet associé au post dans la base de données.
     */
    private Long topicId;

    /**
     * L'identifiant de l'utilisateur qui a créé le post.
     * Il correspond à l'identifiant de l'utilisateur qui a créé le post dans la base de données.
     */
    private Long userId;

    /**
     * Le nom d'utilisateur de l'utilisateur qui a créé le post.
     * Il correspond au nom d'utilisateur de l'utilisateur qui a créé le post dans la base de données.
     */
    private String postUsername;
}