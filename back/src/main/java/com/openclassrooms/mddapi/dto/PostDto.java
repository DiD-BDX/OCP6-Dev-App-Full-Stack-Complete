package com.openclassrooms.mddapi.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

import jakarta.validation.constraints.Max;

/**
 * Classe PostDto utilisée pour le transfert de données entre les différentes couches de l'application.
 * Cette classe est une représentation simplifiée de l'entité Post, utilisée pour exposer uniquement les données nécessaires.
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    /**
     * L'identifiant unique du post. Cet identifiant est généré automatiquement lors de la création d'un nouveau post.
     */
    private Long id;

    /**
     * Le titre du post. Ce champ est obligatoire et ne peut pas dépasser 70 caractères.
     */
    @NonNull
    @Max(70)
    private String title;

    /**
     * Le contenu du post. Ce champ est obligatoire.
     */
    @NonNull
    private String content;

    /**
     * La date de création du post. Cette date est générée automatiquement lors de la création du post.
     */
    private Date createdAt;

    /**
     * La date de dernière mise à jour du post. Cette date est mise à jour automatiquement chaque fois que le post est modifié.
     */
    private Date updatedAt;

    /**
     * L'identifiant du sujet associé au post. Ce champ est utilisé pour lier le post à un sujet spécifique.
     */
    private Long topicId;

    /**
     * L'identifiant de l'utilisateur qui a créé le post. Ce champ est utilisé pour lier le post à l'utilisateur qui l'a créé.
     */
    private Long userId;

    private String postUsername;
}