package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Le {@link Repository} pour les {@link Subscriptions}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour fournir des méthodes de gestion de base de données pour les {@link Subscriptions}.
 * </p>
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {
    /**
     * Trouve une {@link Optional} de {@link Subscriptions} par leur userId et topicId.
     *
     * @param userId L'identifiant de l'utilisateur pour lequel l'abonnement est recherché.
     * @param topicId L'identifiant du sujet pour lequel l'abonnement est recherché.
     * @return Une {@link Optional} de {@link Subscriptions} pour l'utilisateur et le sujet spécifiés.
     */
    Optional<Subscriptions> findByUserIdAndTopicId(Long userId, Long topicId);

    /**
     * Trouve une liste de {@link Subscriptions} par leur userId.
     *
     * @param userId L'identifiant de l'utilisateur pour lequel les abonnements sont recherchés.
     * @return Une liste de {@link Subscriptions} pour l'utilisateur spécifié.
     */
    List<Subscriptions> findByUserId(Long userId);
}