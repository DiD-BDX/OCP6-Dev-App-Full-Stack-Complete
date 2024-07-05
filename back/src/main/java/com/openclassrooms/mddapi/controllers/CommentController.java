package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Contrôleur pour les opérations sur les commentaires.
 * <p>
 * Ce contrôleur gère les requêtes pour obtenir et créer des commentaires.
 * @see com.openclassrooms.mddapi.controllers
 */
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructeur pour CommentController.
     *
     * @param commentService Le service de commentaires.
     * @see CommentService
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Obtient les commentaires pour un post spécifique.
     *
     * @param postId L'ID du post.
     * @return Une liste de commentaires.
     * @see CommentDto
     */
    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    /**
     * Crée un nouveau commentaire pour un post spécifique.
     *
     * @param postId L'ID du post.
     * @param commentDto Les détails du commentaire.
     * @return Le commentaire créé.
     * @see CommentDto
     */
    @PostMapping("/post/{postId}/comments")
    public CommentDto createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        return commentService.createComment(postId, commentDto, commentDto.getUserId());
    }
}