package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Le {@link Repository} pour les {@link Topic}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour fournir des méthodes de gestion de base de données pour les {@link Topic}.
 * </p>
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Recherche un {@link Topic} par son nom.
     *
     * @param name Le nom du {@link Topic} à rechercher.
     * @return Un {@link Optional} contenant le {@link Topic} si trouvé, sinon {@link Optional} vide.
     */
    Optional<Topic> findByName(String name);

    /**
     * Vérifie si un {@link Topic} existe avec le nom spécifié.
     *
     * @param name Le nom du {@link Topic} à vérifier.
     * @return true si un {@link Topic} avec le nom spécifié existe, sinon false.
     */
    Boolean existsByName(String name);
}