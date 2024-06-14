package com.openclassrooms.mddapi.models;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.util.Date;

/**
 * Représente un commentaire dans la base de données.
 */
@Entity
@Table(name = "COMMENT")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    /**
     * L'identifiant unique du commentaire.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Le contenu du commentaire.
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * La date et l'heure de la création du commentaire.
     */
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * L'utilisateur qui a écrit le commentaire.
     * 
     * @JoinColumn indique que la colonne 'user_id' dans la table 'COMMENT' est une clé étrangère qui fait référence à la colonne 'id' de la table 'USER'.
     * 'nullable = false' signifie que chaque commentaire doit avoir un utilisateur associé ; il ne peut pas être null.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Le post qui a été commenté.
     * 
     * @JoinColumn indique que la colonne 'post_id' dans la table 'COMMENT' est une clé étrangère qui fait référence à la colonne 'id' de la table 'POST'.
     * 'nullable = false' signifie que chaque commentaire doit être associé à un post ; il ne peut pas être null.
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}