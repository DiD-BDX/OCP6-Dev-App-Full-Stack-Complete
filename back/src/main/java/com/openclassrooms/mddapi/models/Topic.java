package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Représente un sujet dans la base de données.
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
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le nom du sujet. Ce champ est obligatoire.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * La description du sujet.
     */
    @Column(name = "description")
    private String description;
}
