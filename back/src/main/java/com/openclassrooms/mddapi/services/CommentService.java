package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;

/**
 * Service pour gérer les opérations liées aux commentaires.
 * Ce service utilise {@link com.openclassrooms.mddapi.repository.CommentRepository} pour interagir avec la base de données,
 * {@link com.openclassrooms.mddapi.mapper.CommentMapper} pour convertir les entités en DTO et vice versa,
 * {@link com.openclassrooms.mddapi.repository.PostRepository} pour récupérer les informations sur les posts,
 * et {@link com.openclassrooms.mddapi.services.UserService} pour récupérer les informations sur les utilisateurs.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final UserService userService;

    /**
     * Constructeur pour l'injection de dépendances.
     * @param commentRepository Le repository pour les commentaires.
     * @param commentMapper Le mapper pour les commentaires.
     * @param postRepository Le repository pour les posts.
     * @param userService Le service pour les utilisateurs.
     */
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, PostRepository postRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    /**
     * Récupère tous les commentaires d'un post spécifique.
     * @param postId L'ID du post pour lequel récupérer les commentaires.
     * @return Une liste de {@link com.openclassrooms.mddapi.dto.CommentDto} représentant les commentaires du post.
     */
    public List<CommentDto> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Convertit une entité Comment en CommentDto.
     * @param comment L'entité {@link com.openclassrooms.mddapi.models.Comment} à convertir.
     * @return Un {@link com.openclassrooms.mddapi.dto.CommentDto} représentant le commentaire.
     */
    private CommentDto convertToDto(Comment comment) {
        CommentDto dto = commentMapper.toDto(comment);
        dto.setCommentUsername(userService.findUsernameById(comment.getUser().getId()));
        return dto;
    }

    /**
     * Crée un nouveau commentaire.
     * @param postId L'ID du post auquel le commentaire est associé.
     * @param commentDto Le {@link com.openclassrooms.mddapi.dto.CommentDto} représentant le commentaire à créer.
     * @param userId L'ID de l'utilisateur qui crée le commentaire.
     * @return Un {@link com.openclassrooms.mddapi.dto.CommentDto} représentant le commentaire créé.
     */
    public CommentDto createComment(Long postId, CommentDto commentDto, Long userId) {
        Post post = getPostById(postId);
        User user = getUserById(userId);

        Comment comment = createCommentFromDto(commentDto, post, user);
        Comment savedComment = commentRepository.save(comment);

        return convertToDto(savedComment);
    }

    /**
     * Récupère un post par son ID.
     * @param postId L'ID du post à récupérer.
     * @return L'entité {@link com.openclassrooms.mddapi.models.Post} représentant le post.
     * @throws NoSuchElementException si aucun post avec cet ID n'est trouvé.
     */
    private Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post avec l'ID " + postId + " non trouvé."));
    }

    /**
     * Récupère un utilisateur par son ID.
     * @param userId L'ID de l'utilisateur à récupérer.
     * @return L'entité {@link com.openclassrooms.mddapi.models.User} représentant l'utilisateur.
     * @throws NoSuchElementException si aucun utilisateur avec cet ID n'est trouvé.
     */
    private User getUserById(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("Utilisateur avec l'ID " + userId + " non trouvé.");
        }
        return user;
    }

    /**
     * Crée une entité Comment à partir d'un CommentDto.
     * @param commentDto Le {@link com.openclassrooms.mddapi.dto.CommentDto} à partir duquel créer l'entité Comment.
     * @param post Le post auquel le commentaire est associé.
     * @param user L'utilisateur qui crée le commentaire.
     * @return L'entité {@link com.openclassrooms.mddapi.models.Comment} créée.
     */
    private Comment createCommentFromDto(CommentDto commentDto, Post post, User user) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setPost(post);
        comment.setUser(user);
        return comment;
    }
}