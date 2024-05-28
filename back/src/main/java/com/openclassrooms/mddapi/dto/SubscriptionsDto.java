package com.openclassrooms.mddapi.dto;

import lombok.*;

import java.util.Date;

/**
 * Représente une souscription pour le transfert de données.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionsDto {

    /**
     * L'identifiant unique de la souscription.
     */
    private Long id;

    /**
     * L'identifiant de l'utilisateur qui a souscrit.
     */
    private Long userId;

    /**
     * L'identifiant du sujet auquel l'utilisateur a souscrit.
     */
    private Long topicId;

    /**
     * La date à laquelle l'utilisateur a souscrit.
     */
    private Date subscribedAt;
}