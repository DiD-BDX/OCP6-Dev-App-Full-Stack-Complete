package com.openclassrooms.mddapi.dto;

import lombok.*;

import java.util.Date;

/**
 * Représente un post pour le transfert de données.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    /**
     * L'identifiant unique du post.
     */
    private Long id;

    /**
     * Le titre du post.
     */
    private String title;

    /**
     * Le contenu du post.
     */
    private String content;

    /**
     * La date de création du post.
     */
    private Date createdAt;

    /**
     * La date de dernière mise à jour du post.
     */
    private Date updatedAt;

    /**
     * L'identifiant du sujet associé au post.
     */
    private Long topicId;
}
