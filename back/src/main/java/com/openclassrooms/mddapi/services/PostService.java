package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

    public List<PostDto> getPostsByTopic(Long topicId, Long userId) {
        List<SubscriptionsDto> userSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);

        boolean isSubscribed = userSubscriptions.stream().anyMatch(subscription -> subscription.getTopicId().equals(topicId));

        if (!isSubscribed) {
            throw new RuntimeException("User is not subscribed to this topic");
        }

        List<Post> posts = postRepository.findByTopicIdOrderByCreatedAtDesc(topicId);
        return posts.stream().map(post -> {
            PostDto dto = postMapper.toDto(post);
            dto.setPostUsername(userService.findUsernameById(post.getUser().getId()));
            return dto;
        }).collect(Collectors.toList());
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
        Topic topic = topicRepository.findById(postDto.getTopicId()).orElseThrow(() -> new NoSuchElementException("Topic avec l'ID " + postDto.getTopicId() + " non trouvé."));
        User user = userService.findById(postDto.getUserId());
        if (user == null) {
            throw new NoSuchElementException("Utilisateur avec l'ID " + postDto.getUserId() + " non trouvé.");
        }


        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setUpdatedAt(postDto.getUpdatedAt());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTopic(topic);
        post.setUser(user);
    
        Post savedPost = postRepository.save(post);
        PostDto savedDto = postMapper.toDto(savedPost);
        savedDto.setPostUsername(userService.findUsernameById(savedPost.getUser().getId()));

        System.out.println("-------- saved Dto: " + savedDto); 
        return savedDto;
    }
}