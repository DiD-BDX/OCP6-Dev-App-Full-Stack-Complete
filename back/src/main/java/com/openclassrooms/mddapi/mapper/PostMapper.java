package com.openclassrooms.mddapi.mapper;

import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;

/**
 * Mapper pour convertir entre {@link PostDto} et {@link Post}.
 * <p>
 * Cette interface étend {@link EntityMapper}, qui définit des méthodes pour mapper entre
 * un type de DTO et un type d'entité. Les types spécifiques pour cette interface sont {@link PostDto} pour le DTO
 * et {@link Post} pour l'entité.
 * <p>
 * Cette interface est annotée avec {@link Component} pour indiquer à Spring que c'est un bean et
 * peut être injectée où nécessaire. Elle est également annotée avec {@link Mapper} pour indiquer à MapStruct
 * qu'il s'agit d'un mapper et que MapStruct doit générer une implémentation à la compilation.
 */
@Component
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDto, Post> {
    @Mapping(source = "topic.id", target = "topicId")
    PostDto toDto(Post post);

    @Mapping(source = "topicId", target = "topic.id")
    Post toEntity(PostDto postDto);
}
