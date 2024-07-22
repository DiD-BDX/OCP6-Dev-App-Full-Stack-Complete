package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;

/**
 * Interface pour le mapper entre l'entité {@link com.openclassrooms.mddapi.models.Post} et son DTO {@link com.openclassrooms.mddapi.dto.PostDto}.
 * Ce mapper utilise MapStruct pour effectuer les conversions.
 * L'annotation {@link org.mapstruct.Mapper} indique que cette interface est un mapper MapStruct.
 * L'attribut componentModel = "spring" indique que l'implémentation doit être un bean Spring.
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    /**
     * Convertit l'entité {@link com.openclassrooms.mddapi.models.Post} en son DTO {@link com.openclassrooms.mddapi.dto.PostDto}.
     * Les annotations {@link org.mapstruct.Mapping} indiquent comment les champs de l'entité sont mappés sur les champs du DTO.
     * @param post l'entité à convertir
     * @return le DTO converti
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "topic.id", target = "topicId")
    //@Mapping(target="postUsername", ignore=true)
    @Mapping(target="postUsername", expression="java(post.getUser().getUsername())")
    PostDto toDto(Post post);

    /**
     * Convertit le {@link com.openclassrooms.mddapi.dto.PostDto} en son entité {@link com.openclassrooms.mddapi.models.Post}.
     * Les annotations {@link org.mapstruct.Mapping} indiquent comment les champs du DTO sont mappés sur les champs de l'entité.
     * @param postDto le DTO à convertir
     * @return l'entité convertie
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "topicId", target = "topic.id")
    Post toEntity(PostDto postDto);
}