package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subscriptions;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service pour gérer les opérations sur les posts.
 * 
 * @see Post
 * @see PostRepository
 */
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final SubscriptionService subscriptionService;
    private final TopicRepository topicRepository;
    private final UserService userService;

    /**
     * Obtient les posts par sujet.
     * 
     * @param topicId l'ID du sujet
     * @param userId l'ID de l'utilisateur
     * @return une liste d'objets {@link Post}
     */
    public List<Post> getPostsByTopic(Long topicId, Long userId) {
        validateUserSubscription(topicId, userId);
        return postRepository.findByTopicIdOrderByCreatedAtDesc(topicId);
    }

    /**
     * Valide l'abonnement de l'utilisateur.
     * 
     * @param topicId l'ID du sujet
     * @param userId l'ID de l'utilisateur
     * @throws RuntimeException si l'utilisateur n'est pas abonné au sujet
     */
    private void validateUserSubscription(long topicId, Long userId) {
        List<Subscriptions> userSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        System.out.println("-------validateUserSubscription----liste des abonnements:  " + userSubscriptions); // Afficher la liste dans la console
        boolean isSubscribed = userSubscriptions.stream().anyMatch(subscription -> subscription.getTopic().getId().equals(topicId));

        if (!isSubscribed) {
            throw new RuntimeException("User is not subscribed to this topic");
        }
    }

    /**
     * Obtient les posts par les abonnements d'un utilisateur.
     * 
     * @param userId l'ID de l'utilisateur
     * @return une liste d'objets {@link Post}
     */
    public List<Post> getPostsByUserSubscriptions(Long userId) {
        List<Subscriptions> userSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        List<Post> posts = new ArrayList<>();
        for (Subscriptions subscription : userSubscriptions) {
            Long topicId = subscription.getTopic().getId();
            posts.addAll(getPostsByTopic(topicId, userId));
        }
        return posts;
    }

    /**
     * Crée un post.
     * 
     * @param post l'objet {@link Post} à créer
     * @return l'objet {@link Post} créé
     */
    public Post createPost(Post post) {
        Topic topic = getTopicById(post.getTopic().getId());

        User user = getUserById(post.getUser().getId());

        post.setTopic(topic);
        post.setUser(user);
        
        return postRepository.save(post);
    }

    /**
     * Obtient un sujet par ID.
     * 
     * @param topicId l'ID du sujet
     * @return l'objet {@link Topic}
     * @throws NoSuchElementException si le sujet n'est pas trouvé
     */
    private Topic getTopicById(Long topicId) {
        return topicRepository.findById(topicId).orElseThrow(() -> new NoSuchElementException("Topic avec l'ID " + topicId + " non trouvé."));
    }

    /**
     * Obtient un utilisateur par ID.
     * 
     * @param userId l'ID de l'utilisateur
     * @return l'objet {@link User}
     * @throws NoSuchElementException si l'utilisateur n'est pas trouvé
     */
    private User getUserById(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("Utilisateur avec l'ID " + userId + " non trouvé.");
        }
        return user;
    }
}