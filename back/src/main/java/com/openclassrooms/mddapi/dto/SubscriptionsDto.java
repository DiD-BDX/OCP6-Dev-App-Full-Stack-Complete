package com.openclassrooms.mddapi.dto;

import lombok.*;

import java.util.Date;

/**
 * La classe SubscriptionsDto est un objet de transfert de données (DTO) pour le transfert de données de Subscriptions.
 * Elle est utilisée pour transférer des données entre les couches de l'application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionsDto {

    /**
     * L'identifiant unique de la souscription.
     * Il correspond à l'identifiant de la souscription dans la base de données.
     */
    private Long id;

    /**
     * L'identifiant de l'utilisateur qui a souscrit.
     * Il correspond à l'identifiant de l'utilisateur qui a souscrit dans la base de données.
     */
    private Long userId;

    /**
     * L'identifiant du sujet auquel l'utilisateur a souscrit.
     * Il correspond à l'identifiant du sujet auquel l'utilisateur a souscrit dans la base de données.
     */
    private Long topicId;

    /**
     * La date à laquelle l'utilisateur a souscrit.
     * Elle correspond à la date à laquelle l'utilisateur a souscrit dans la base de données.
     */
    private Date subscribedAt;

    /**
     * Le nom du sujet auquel l'utilisateur a souscrit.
     * Il correspond au nom du sujet auquel l'utilisateur a souscrit dans la base de données.
     */
    private String topicName;

    /**
     * La description du sujet auquel l'utilisateur a souscrit.
     * Elle correspond à la description du sujet auquel l'utilisateur a souscrit dans la base de données.
     */
    private String topicDescription;
}