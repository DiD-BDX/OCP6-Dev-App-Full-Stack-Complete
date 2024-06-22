package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.mapper.PostMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service class for managing posts.
 * 
 * @see Post
 * @see PostDto
 * @see PostRepository
 * @see PostMapper
 */
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final SubscriptionService subscriptionService;
    private final TopicRepository topicRepository;
    private final UserService userService;

    /**
     * Constructor for PostService.
     * 
     * @param postRepository the {@link PostRepository} to use
     * @param postMapper the {@link PostMapper} to use
     * @param subscriptionService the {@link SubscriptionService} to use
     * @param topicRepository the {@link TopicRepository} to use
     * @param userService the {@link UserService} to use
     */
    public PostService(PostRepository postRepository, PostMapper postMapper, SubscriptionService subscriptionService, TopicRepository topicRepository, UserService userService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.subscriptionService = subscriptionService;
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    /**
     * Get posts by topic.
     * 
     * @param topicId the ID of the topic
     * @param userId the ID of the user
     * @return a list of {@link PostDto} objects
     */
    public List<PostDto> getPostsByTopic(Long topicId, Long userId) {
        validateUserSubscription(topicId, userId);

        List<Post> posts = postRepository.findByTopicIdOrderByCreatedAtDesc(topicId);
        return posts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Validate user subscription.
     * 
     * @param topicId the ID of the topic
     * @param userId the ID of the user
     */
    private void validateUserSubscription(Long topicId, Long userId) {
        List<SubscriptionsDto> userSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        boolean isSubscribed = userSubscriptions.stream().anyMatch(subscription -> subscription.getTopicId().equals(topicId));

        if (!isSubscribed) {
            throw new RuntimeException("User is not subscribed to this topic");
        }
    }

    /**
     * Convert a {@link Post} object to a {@link PostDto} object.
     * 
     * @param post the {@link Post} object to convert
     * @return the converted {@link PostDto} object
     */
    private PostDto convertToDto(Post post) {
        PostDto dto = postMapper.toDto(post);
        dto.setPostUsername(userService.findUsernameById(post.getUser().getId()));
        return dto;
    }

    /**
     * Get posts by user subscriptions.
     * 
     * @param userId the ID of the user
     * @return a list of {@link PostDto} objects
     */
    public List<PostDto> getPostsByUserSubscriptions(Long userId) {
        List<SubscriptionsDto> userSubscriptions = subscriptionService.getSubscriptionsByUserId(userId);

        List<PostDto> posts = new ArrayList<>();
        for (SubscriptionsDto subscription : userSubscriptions) {
            Long topicId = subscription.getTopicId();
            posts.addAll(getPostsByTopic(topicId, userId));
        }

        return posts;
    }

    /**
     * Create a post.
     * 
     * @param postDto the {@link PostDto} object to create the post from
     * @return the created {@link PostDto} object
     */
    public PostDto createPost(PostDto postDto) {
        Topic topic = getTopicById(postDto.getTopicId());
        User user = getUserById(postDto.getUserId());

        Post post = createPostFromDto(postDto, topic, user);
        Post savedPost = postRepository.save(post);

        return convertToDto(savedPost);
    }

    /**
     * Get a topic by ID.
     * 
     * @param topicId the ID of the topic
     * @return the {@link Topic} object
     */
    private Topic getTopicById(Long topicId) {
        return topicRepository.findById(topicId).orElseThrow(() -> new NoSuchElementException("Topic avec l'ID " + topicId + " non trouvé."));
    }

    /**
     * Get a user by ID.
     * 
     * @param userId the ID of the user
     * @return the {@link User} object
     */
    private User getUserById(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("Utilisateur avec l'ID " + userId + " non trouvé.");
        }
        return user;
    }

    /**
     * Create a {@link Post} object from a {@link PostDto} object.
     * 
     * @param postDto the {@link PostDto} object to create the post from
     * @param topic the {@link Topic} object to associate with the post
     * @param user the {@link User} object to associate with the post
     * @return the created {@link Post} object
     */
    private Post createPostFromDto(PostDto postDto, Topic topic, User user) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setUpdatedAt(postDto.getUpdatedAt());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTopic(topic);
        post.setUser(user);
        return post;
    }
}