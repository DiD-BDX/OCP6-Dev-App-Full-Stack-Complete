package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * La classe Subscriptions représente une souscription dans la base de données.
 * Chaque souscription est associée à un utilisateur et à un sujet.
 */
@Entity
@Table(name = "SUBSCRIPTIONS")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subscriptions {

    /**
     * L'identifiant unique de la souscription.
     * C'est la clé primaire dans la table SUBSCRIPTIONS.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'utilisateur qui a souscrit.
     * La colonne 'user_id' dans la table 'SUBSCRIPTIONS' est une clé étrangère qui fait référence à la colonne 'id' de la table 'USER'.
     * Chaque souscription doit avoir un utilisateur associé ; il ne peut pas être null.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Le sujet auquel l'utilisateur a souscrit.
     * La colonne 'topic_id' dans la table 'SUBSCRIPTIONS' est une clé étrangère qui fait référence à la colonne 'id' de la table 'TOPIC'.
     * Chaque souscription doit être associée à un sujet ; il ne peut pas être null.
     */
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    /**
     * La date à laquelle l'utilisateur a souscrit.
     * Stocké en tant que TIMESTAMP dans la base de données.
     */
    @Column(name = "subscribed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscribedAt;
}