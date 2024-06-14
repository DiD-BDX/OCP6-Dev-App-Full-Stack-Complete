package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public List<CommentDto> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> {
            CommentDto dto = commentMapper.toDto(comment);
            dto.setCommentUsername(userService.findUsernameById(comment.getUser().getId()));
            return dto;
        }).collect(Collectors.toList());
    }

    public CommentDto createComment(Long postId, CommentDto commentDto, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post avec l'ID " + postId + " non trouvé."));
        User user = userService.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("Utilisateur avec l'ID " + userId + " non trouvé.");
        }
    
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setPost(post);
        comment.setUser(user);
    
        Comment savedComment = commentRepository.save(comment);
        CommentDto savedDto = commentMapper.toDto(savedComment);
        savedDto.setCommentUsername(userService.findUsernameById(savedComment.getUser().getId()));
        
        System.out.println("-------- saved Dto: " + savedDto); 
        return savedDto;
    }

}
