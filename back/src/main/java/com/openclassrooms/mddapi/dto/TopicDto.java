package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Max;
import lombok.*;

/**
 * La classe TopicDto est un objet de transfert de données (DTO) pour le transfert de données de Topic.
 * Elle est utilisée pour transférer des données entre les couches de l'application.
 * Cette classe est une représentation simplifiée de l'entité Topic, utilisée pour exposer uniquement les données nécessaires.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDto {

    /**
     * L'identifiant unique du sujet.
     * Il correspond à l'identifiant du sujet dans la base de données.
     * Cet identifiant est généré automatiquement lors de la création d'un nouveau sujet.
     */
    private Long id;

    /**
     * Le nom du sujet.
     * Ce champ est obligatoire et ne peut pas dépasser 70 caractères.
     * Il correspond au nom du sujet dans la base de données.
     */
    @NonNull
    @Max(70)
    private String name;

    /**
     * La description du sujet.
     * Ce champ est facultatif et ne peut pas dépasser 255 caractères.
     * Il correspond à la description du sujet dans la base de données.
     */
    @Max(255)
    private String description;
}