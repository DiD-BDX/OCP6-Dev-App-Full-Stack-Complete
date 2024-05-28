package com.openclassrooms.mddapi.dto;

import lombok.*;

/**
 * Représente un sujet pour le transfert de données.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDto {

    /**
     * L'identifiant unique du sujet.
     */
    private Long id;

    /**
     * Le nom du sujet.
     */
    private String name;

    /**
     * La description du sujet.
     */
    private String description;
}
