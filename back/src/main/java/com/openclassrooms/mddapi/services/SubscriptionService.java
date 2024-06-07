package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.mapper.SubscriptionsMapper;
import com.openclassrooms.mddapi.models.Subscriptions;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

        @Autowired
        private SubscriptionRepository subscriptionRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private TopicRepository topicRepository;

        @Autowired
        private SubscriptionsMapper subscriptionsMapper;

        public SubscriptionsDto toDto(Subscriptions subscription) {
                return subscriptionsMapper.toDto(subscription);
        }

        public SubscriptionsDto subscribeUserToTopic(Long userId, Long topicId) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> {
                        return new IllegalArgumentException("Invalid user Id:" + userId);
                        });
                Topic topic = topicRepository.findById(topicId)
                        .orElseThrow(() -> {
                        return new IllegalArgumentException("Invalid topic Id:" + topicId);
                        });

                // Vérifie si l'utilisateur est déjà abonné à ce sujet
                boolean isAlreadySubscribed = subscriptionRepository.findByUserIdAndTopicId(user.getId(), topic.getId()).isPresent();
                if (isAlreadySubscribed) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already subscribed to this topic");
                }

                Subscriptions subscription = new Subscriptions()
                        .setUser(user)
                        .setTopic(topic)
                        .setSubscribedAt(new Date());
                Subscriptions savedSubscription = subscriptionRepository.save(subscription);
                return subscriptionsMapper.toDto(savedSubscription);
        }

        public void unsubscribeUserFromTopic(Long userId, Long topicId) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
                Topic topic = topicRepository.findById(topicId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid topic Id:" + topicId));

                // Trouver l'abonnement de l'utilisateur à ce sujet
                Subscriptions subscription = subscriptionRepository.findByUserIdAndTopicId(user.getId(), topic.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));

                // Supprimer l'abonnement
                subscriptionRepository.delete(subscription);
        }
        public List<Subscriptions> getSubscriptionsByUserId(Long userId) {
                if (!userRepository.existsById(userId)) {
                        throw new IllegalArgumentException("Invalid user Id:" + userId);
                }
                return subscriptionRepository.findByUserId(userId);
        }
}