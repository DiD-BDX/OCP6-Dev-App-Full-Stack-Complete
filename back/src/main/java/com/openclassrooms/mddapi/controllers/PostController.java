package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;

import lombok.RequiredArgsConstructor;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.PostMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur pour les opérations sur les posts.
 * <p>
 * Ce contrôleur gère les requêtes pour obtenir et créer des posts.
 * @see com.openclassrooms.mddapi.controllers
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;
    private final TopicService topicService;

    /**
     * Obtient les posts par les abonnements d'un utilisateur spécifique.
     *
     * @param userId L'ID de l'utilisateur.
     * @return Une liste de PostDto représentant les posts des abonnements de l'utilisateur.
     * @see Post
     */
    @GetMapping("/{userId}/posts")
    public List<PostDto> getPostsByUserSubscriptions(@PathVariable Long userId) {
        List<PostDto> posts = postService.getPostsByUserSubscriptions(userId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
        return posts;
    }

    /**
     * Crée un nouveau post pour un utilisateur et un sujet spécifiques.
     *
     * @param userId L'ID de l'utilisateur.
     * @param topicId L'ID du sujet.
     * @param post Les détails du post à créer.
     * @return Le post créé.
     * @see Post
     */
    @PostMapping("/post/create/{userId}/{topicId}")
    public PostDto createPost(@PathVariable Long userId, @PathVariable Long topicId, @RequestBody Post post) {
        /**
         * Trouve l'utilisateur par son ID.
         * 
         * @param userId L'ID de l'utilisateur à trouver.
         * @return L'utilisateur trouvé.
         */
        User user = userService.findById(userId);
        
        /**
         * Trouve le sujet par son ID.
         * 
         * @param topicId L'ID du sujet à trouver.
         * @return Le sujet trouvé.
         */
        Topic topic = topicService.findById(topicId);

        /**
         * Associe l'utilisateur et le sujet au post.
         * 
         * @param user L'utilisateur à associer au post.
         * @param topic Le sujet à associer au post.
         */
        post.setUser(user);
        post.setTopic(topic);
        
        /**
         * Crée le post.
         * 
         * @param post Les détails du post à créer.
         * @return Le post créé.
         */
        Post createdPost = postService.createPost(post);

        /**
         * Convertit le post créé en DTO.
         * 
         * @param createdPost Le post créé à convertir.
         * @return Le post DTO.
         */
        PostDto postDto = postMapper.toDto(createdPost);

        /**
         * Retourne le post DTO.
         * 
         * @return Le post DTO.
         */
        return postDto;
    }
}