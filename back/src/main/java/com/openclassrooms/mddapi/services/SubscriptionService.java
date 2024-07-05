package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.mapper.SubscriptionsMapper;
import com.openclassrooms.mddapi.models.Subscriptions;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les abonnements.
 */
@Service
public class SubscriptionService {

        private final SubscriptionRepository subscriptionRepository;
        private final UserRepository userRepository;
        private final TopicRepository topicRepository;
        private final SubscriptionsMapper subscriptionsMapper;

        /**
         * Constructeur pour injecter les dépendances.
         *
         * @param subscriptionRepository Le repository pour gérer les abonnements.
         * @param userRepository Le repository pour gérer les utilisateurs.
         * @param topicRepository Le repository pour gérer les sujets.
         * @param subscriptionsMapper Le mapper pour convertir les abonnements en DTO.
         */
        public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, TopicRepository topicRepository, SubscriptionsMapper subscriptionsMapper) {
                this.subscriptionRepository = subscriptionRepository;
                this.userRepository = userRepository;
                this.topicRepository = topicRepository;
                this.subscriptionsMapper = subscriptionsMapper;
        }

        /**
         * Convertit un abonnement en DTO.
         *
         * @param subscription L'abonnement à convertir.
         * @return Le DTO correspondant à l'abonnement.
         */
        public SubscriptionsDto toDto(Subscriptions subscription) {
                return subscriptionsMapper.toDto(subscription);
        }

        /**
         * Abonne un utilisateur à un sujet.
         *
         * @param userId L'ID de l'utilisateur.
         * @param topicId L'ID du sujet.
         * @return Le DTO de l'abonnement créé.
         * @throws ResponseStatusException Si l'utilisateur est déjà abonné à ce sujet.
         */
        public SubscriptionsDto subscribeUserToTopic(Long userId, Long topicId) {
                User user = getUserById(userId);
                Topic topic = getTopicById(topicId);

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

        /**
         * Désabonne un utilisateur d'un sujet.
         *
         * @param userId L'ID de l'utilisateur.
         * @param topicId L'ID du sujet.
         * @throws ResponseStatusException Si l'abonnement n'est pas trouvé.
         */
        public void unsubscribeUserFromTopic(Long userId, Long topicId) {
                User user = getUserById(userId);
                Topic topic = getTopicById(topicId);

                Subscriptions subscription = subscriptionRepository.findByUserIdAndTopicId(user.getId(), topic.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));

                subscriptionRepository.delete(subscription);
        }

        /**
         * Récupère les abonnements d'un utilisateur.
         *
         * @param userId L'ID de l'utilisateur.
         * @return La liste des DTO des abonnements de l'utilisateur.
         * @throws IllegalArgumentException Si l'ID de l'utilisateur est invalide.
         */
        public List<SubscriptionsDto> getSubscriptionsByUserId(Long userId) {
                if (!userRepository.existsById(userId)) {
                throw new IllegalArgumentException("Invalid user Id:" + userId);
                }
                List<Subscriptions> subscriptions = subscriptionRepository.findByUserId(userId);
                return subscriptions.stream().map(subscriptionsMapper::toDto).collect(Collectors.toList());
        }

        /**
         * Récupère un utilisateur par son ID.
         *
         * @param userId L'ID de l'utilisateur.
         * @return L'utilisateur.
         * @throws IllegalArgumentException Si l'ID de l'utilisateur est invalide.
         */
        private User getUserById(Long userId) {
                return userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        }

        /**
         * Récupère un sujet par son ID.
         *
         * @param topicId L'ID du sujet.
         * @return Le sujet.
         * @throws IllegalArgumentException Si l'ID du sujet est invalide.
         */
        private Topic getTopicById(Long topicId) {
                return topicRepository.findById(topicId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid topic Id:" + topicId));
        }
}