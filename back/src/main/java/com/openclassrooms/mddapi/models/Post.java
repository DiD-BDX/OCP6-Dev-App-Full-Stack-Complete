package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * Représente un post dans la base de données.
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
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le titre du post. Ce champ est obligatoire et limité à 100 caractères.
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /**
     * Le contenu du post. Ce champ est obligatoire.
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /**
     * La date de création du post.
     */
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * La date de dernière mise à jour du post.
     */
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /**
     * Le sujet associé au post.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    /**
     * L'utilisateur qui a écrit le post.
     * 
     * @JoinColumn indique que la colonne 'user_id' dans la table 'POST' est une clé étrangère qui fait référence à la colonne 'id' de la table 'USER'.
     * 'nullable = false' signifie que chaque post doit avoir un utilisateur associé ; il ne peut pas être null.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
