package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * La classe Post représente un post dans la base de données.
 * Chaque post est associé à un utilisateur et à un sujet.
 */
@Entity
@Table(name = "POST")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

    /**
     * L'identifiant unique du post.
     * C'est la clé primaire dans la table POST.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le titre du post.
     * Ce champ est obligatoire et limité à 255 caractères.
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /**
     * Le contenu du post.
     * Ce champ est obligatoire et est stocké en tant que TEXT dans la base de données.
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /**
     * La date de création du post.
     * Stocké en tant que TIMESTAMP dans la base de données.
     */
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * La date de dernière mise à jour du post.
     * Stocké en tant que TIMESTAMP dans la base de données.
     */
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /**
     * Le sujet associé au post.
     * La colonne 'topic_id' dans la table 'POST' est une clé étrangère qui fait référence à la colonne 'id' de la table 'TOPIC'.
     * Chaque post doit être associé à un sujet ; il ne peut pas être null.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    /**
     * L'utilisateur qui a écrit le post.
     * La colonne 'user_id' dans la table 'POST' est une clé étrangère qui fait référence à la colonne 'id' de la table 'USER'.
     * Chaque post doit avoir un utilisateur associé ; il ne peut pas être null.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}