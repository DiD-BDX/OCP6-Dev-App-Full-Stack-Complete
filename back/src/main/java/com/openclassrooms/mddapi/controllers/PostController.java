package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour les opérations sur les posts.
 * <p>
 * Ce contrôleur gère les requêtes pour obtenir et créer des posts.
 * @see com.openclassrooms.mddapi.controllers
 */
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    /**
     * Constructeur pour PostController.
     *
     * @param postService Le service de posts.
     * @see PostService
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Obtient les posts par les abonnements d'un utilisateur spécifique.
     *
     * @param userId L'ID de l'utilisateur.
     * @return Une liste de posts.
     * @see PostDto
     */
    @GetMapping("/{userId}/posts")
    public List<PostDto> getPostsByUserSubscriptions(@PathVariable Long userId) {
        return postService.getPostsByUserSubscriptions(userId);
    }

    /**
     * Crée un nouveau post pour un utilisateur et un sujet spécifiques.
     *
     * @param userId L'ID de l'utilisateur.
     * @param topicId L'ID du sujet.
     * @param postDto Les détails du post.
     * @return Le post créé.
     * @see PostDto
     */
    @PostMapping("/post/create/{userId}/{topicId}")
    public PostDto createPost(@PathVariable Long userId, @PathVariable Long topicId, @RequestBody PostDto postDto) {
        postDto.setUserId(userId);
        postDto.setTopicId(topicId);

        return postService.createPost(postDto);
    }
}