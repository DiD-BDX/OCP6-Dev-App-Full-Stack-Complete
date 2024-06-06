package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subscriptions;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {
    Optional<Subscriptions> findByUserAndTopic(User user, Topic topic);
    List<Subscriptions> findByUser(User user);
}