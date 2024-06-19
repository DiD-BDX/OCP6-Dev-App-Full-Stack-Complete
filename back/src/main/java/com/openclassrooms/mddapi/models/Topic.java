package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * La classe Topic représente un sujet dans la base de données.
 * Chaque sujet peut être associé à plusieurs posts.
 */
@Entity
@Table(name = "TOPIC")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Topic {

    /**
     * L'identifiant unique du sujet.
     * C'est la clé primaire dans la table TOPIC.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le nom du sujet.
     * Ce champ est obligatoire et est stocké en tant que VARCHAR(255) dans la base de données.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * La description du sujet.
     * Ce champ est facultatif et est stocké en tant que TEXT dans la base de données.
     */
    @Column(name = "description")
    private String description;
}