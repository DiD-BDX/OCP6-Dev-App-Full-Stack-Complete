package com.openclassrooms.mddapi.dto;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * La classe CommentDto est un objet de transfert de données (DTO) pour le transfert de données de Comment.
 * Il est utilisé pour transférer des données entre les couches de l'application.
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    /**
     * L'identifiant unique du commentaire.
     * Il correspond à l'identifiant du commentaire dans la base de données.
     */
    private Long id;

    /**
     * Le contenu du commentaire.
     * Il correspond au contenu du commentaire dans la base de données.
     */
    private String content;

    /**
     * La date et l'heure de la création du commentaire.
     * Elle correspond à la date et à l'heure de la création du commentaire dans la base de données.
     */
    private Date createdAt;

    /**
     * L'identifiant de l'utilisateur qui a écrit le commentaire.
     * Il correspond à l'identifiant de l'utilisateur qui a écrit le commentaire dans la base de données.
     */
    private Long userId;

    /**
     * Le nom d'utilisateur de l'utilisateur qui a écrit le commentaire.
     * Il correspond au nom d'utilisateur de l'utilisateur qui a écrit le commentaire dans la base de données.
     */
    private String commentUsername;

    /**
     * L'identifiant du post qui a été commenté.
     * Il correspond à l'identifiant du post qui a été commenté dans la base de données.
     */
    private Long postId;
}