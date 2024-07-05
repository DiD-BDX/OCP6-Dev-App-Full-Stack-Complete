package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Le {@link Repository} pour les {@link Post}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour fournir des méthodes de gestion de base de données pour les {@link Post}.
 * </p>
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Trouve une liste de {@link Post} par leur userId.
     *
     * @param userId L'identifiant de l'utilisateur pour lequel les posts sont recherchés.
     * @return Une liste de {@link Post} pour l'utilisateur spécifié.
     */
    List<Post> findByUserId(Long userId);

    /**
     * Trouve une liste de {@link Post} par leur topicId.
     *
     * @param topicId L'identifiant du sujet pour lequel les posts sont recherchés.
     * @return Une liste de {@link Post} pour le sujet spécifié.
     */
    List<Post> findByTopicId(Long topicId);

    /**
     * Trouve une liste de {@link Post} par leur userId et topicId.
     *
     * @param userId L'identifiant de l'utilisateur pour lequel les posts sont recherchés.
     * @param topicId L'identifiant du sujet pour lequel les posts sont recherchés.
     * @return Une liste de {@link Post} pour l'utilisateur et le sujet spécifiés.
     */
    List<Post> findByUserIdAndTopicId(Long userId, Long topicId);

    /**
     * Trouve une liste de {@link Post} par leur date de création.
     *
     * @param createdAt La date de création pour laquelle les posts sont recherchés.
     * @return Une liste de {@link Post} pour la date de création spécifiée.
     */
    List<Post> findByCreatedAt(Date createdAt);

    /**
     * Trouve une liste de {@link Post} par leur topicId et les ordonne par date de création descendante.
     *
     * @param topicId L'identifiant du sujet pour lequel les posts sont recherchés.
     * @return Une liste de {@link Post} pour le sujet spécifié, ordonnée par date de création descendante.
     */
    List<Post> findByTopicIdOrderByCreatedAtDesc(Long topicId);
}