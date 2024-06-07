package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {
    Optional<Subscriptions> findByUserIdAndTopicId(Long userId, Long topicId);
    List<Subscriptions> findByUserId(Long userId);
}