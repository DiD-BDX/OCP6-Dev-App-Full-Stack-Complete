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
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    /**
     * Le contenu du post. Ce champ est obligatoire.
     */
    @Column(name = "content", nullable = false)
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
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}
