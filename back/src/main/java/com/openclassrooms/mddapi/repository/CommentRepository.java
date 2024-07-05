package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.models.Comment;

/**
 * Le {@link Repository} pour les {@link Comment}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour fournir des méthodes de gestion de base de données pour les {@link Comment}.
 * </p>
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Trouve une liste de {@link Comment} par leur postId.
     *
     * @param postId L'identifiant du post pour lequel les commentaires sont recherchés.
     * @return Une liste de {@link Comment} pour le post spécifié.
     */
    List<Comment> findByPostId(Long postId);
}