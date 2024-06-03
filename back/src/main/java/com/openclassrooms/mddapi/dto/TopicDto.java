package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Max;
import lombok.*;

/**
 * Classe TopicDto utilisée pour le transfert de données entre les différentes couches de l'application.
 * Cette classe est une représentation simplifiée de l'entité Topic, utilisée pour exposer uniquement les données nécessaires.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDto {

    /**
     * L'identifiant unique du sujet. Cet identifiant est généré automatiquement lors de la création d'un nouveau sujet.
     */
    private Long id;

    /**
     * Le nom du sujet. Ce champ est obligatoire et ne peut pas dépasser 70 caractères.
     */
    @NonNull
    @Max(70)
    private String name;

    /**
     * La description du sujet. Ce champ est facultatif et ne peut pas dépasser 255 caractères.
     */
    @Max(255)
    private String description;
}
