package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;

/**
 * Interface pour le mapper entre l'entité {@link com.openclassrooms.mddapi.models.Topic} et son DTO {@link com.openclassrooms.mddapi.dto.TopicDto}.
 * Ce mapper est un composant de Spring et utilise MapStruct pour effectuer les conversions.
 * L'annotation {@link org.springframework.stereotype.Component} indique que cette interface est un composant Spring.
 * L'annotation {@link org.mapstruct.Mapper} indique que cette interface est un mapper MapStruct.
 * Cette interface étend {@link com.openclassrooms.mddapi.mapper.EntityMapper}, donc elle hérite des méthodes toDto et toEntity.
 */
@Component
@Mapper(componentModel = "spring")
public interface TopicMapper extends EntityMapper<TopicDto, Topic> {
    // Les méthodes toDto et toEntity sont héritées de EntityMapper
}