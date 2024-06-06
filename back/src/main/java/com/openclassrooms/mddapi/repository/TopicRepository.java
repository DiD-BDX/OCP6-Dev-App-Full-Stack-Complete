package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour l'entité Topic.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Recherche un topic par son titre.
     *
     * @param name Le titre du topic à rechercher.
     * @return Un Optional contenant le topic si trouvé, sinon Optional vide.
     */
    Optional<Topic> findByName(String name);

    /**
     * Vérifie si un topic existe avec le titre spécifié.
     *
     * @param name Le titre du topic à vérifier.
     * @return true si un topic avec le titre spécifié existe, sinon false.
     */
    Boolean existsByName(String name);
}
