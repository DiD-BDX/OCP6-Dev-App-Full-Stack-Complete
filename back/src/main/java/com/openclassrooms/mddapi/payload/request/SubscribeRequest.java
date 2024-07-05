package com.openclassrooms.mddapi.payload.request;

import lombok.Data;

/**
 * Classe représentant une demande d'abonnement.
 * <p>
 * Cette classe est utilisée pour transporter l'identifiant de l'utilisateur qui souhaite s'abonner.
 * Elle utilise l'annotation {@link Data} de Lombok pour générer automatiquement les getters et setters.
 * 
 * @see Data
 */
@Data
public class SubscribeRequest {
    
    /**
     * L'identifiant de l'utilisateur.
     * <p>
     * Ce champ est utilisé pour identifier l'utilisateur qui souhaite s'abonner.
     */
    private Long userId;
}