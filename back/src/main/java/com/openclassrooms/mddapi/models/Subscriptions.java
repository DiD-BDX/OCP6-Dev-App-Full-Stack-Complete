package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * Représente une souscription dans la base de données.
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
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'utilisateur qui a souscrit.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Le sujet auquel l'utilisateur a souscrit.
     */
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    /**
     * La date à laquelle l'utilisateur a souscrit.
     */
    @Column(name = "subscribed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscribedAt;
}
