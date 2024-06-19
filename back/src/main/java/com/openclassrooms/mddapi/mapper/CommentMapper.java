package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;

/**
 * Interface pour le mapper entre l'entité {@link com.openclassrooms.mddapi.models.Comment} et son DTO {@link com.openclassrooms.mddapi.dto.CommentDto}.
 * Ce mapper utilise MapStruct pour effectuer les conversions.
 * L'annotation {@link org.mapstruct.Mapper} indique que cette interface est un mapper MapStruct.
 * L'attribut componentModel = "spring" indique que l'implémentation doit être un bean Spring.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    /**
     * Convertit l'entité {@link com.openclassrooms.mddapi.models.Comment} en son DTO {@link com.openclassrooms.mddapi.dto.CommentDto}.
     * Les annotations {@link org.mapstruct.Mapping} indiquent comment les champs de l'entité sont mappés sur les champs du DTO.
     * @param comment l'entité à convertir
     * @return le DTO converti
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(target="commentUsername", ignore=true)
    CommentDto toDto(Comment comment);

    /**
     * Convertit le {@link com.openclassrooms.mddapi.dto.CommentDto} en son entité {@link com.openclassrooms.mddapi.models.Comment}.
     * Les annotations {@link org.mapstruct.Mapping} indiquent comment les champs du DTO sont mappés sur les champs de l'entité.
     * @param commentDto le DTO à convertir
     * @return l'entité convertie
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "post.id")
    Comment toEntity(CommentDto commentDto);
}