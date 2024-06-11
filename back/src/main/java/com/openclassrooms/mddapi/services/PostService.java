package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private SubscriptionService subscriptionService;

    public List<PostDto> getPostsByTopic(Long topicId, Long userId) {
        List<SubscriptionsDto> userSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);

        boolean isSubscribed = userSubscriptions.stream().anyMatch(subscription -> subscription.getTopicId().equals(topicId));

        if (!isSubscribed) {
            throw new RuntimeException("User is not subscribed to this topic");
        }

        List<Post> posts = postRepository.findByTopicIdOrderByCreatedAtDesc(topicId);
        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    public List<PostDto> getPostsByUserSubscriptions(Long userId) {
        // Obtenez d'abord les abonnements de l'utilisateur
        List<SubscriptionsDto> userSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);

        // Ensuite, pour chaque abonnement, obtenez les posts du topic correspondant
        List<PostDto> posts = new ArrayList<>();
        for (SubscriptionsDto subscription : userSubscriptions) {
            Long topicId = subscription.getTopicId();
            posts.addAll(getPostsByTopic(topicId, userId));
        }

        return posts;
    }

    public PostDto createPost(PostDto postDto) {
        // Convertir le DTO en entité
        Post post = postMapper.toEntity(postDto);
    
        // Enregistrer l'entité dans la base de données
        Post savedPost = postRepository.save(post);
    
        // Convertir l'entité enregistrée en DTO
        return postMapper.toDto(savedPost);
    }
}