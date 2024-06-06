package com.openclassrooms.mddapi.dto;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * DTO pour le transfert de données de Comment.
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    /**
     * L'identifiant unique du commentaire.
     */
    private int id;

    /**
     * Le contenu du commentaire.
     */
    private String content;

    /**
     * La date et l'heure de la création du commentaire.
     */
    private Date createdAt;

    /**
     * L'identifiant de l'utilisateur qui a écrit le commentaire.
     */
    private int userId;

    /**
     * L'identifiant du post qui a été commenté.
     */
    private int postId;
}
