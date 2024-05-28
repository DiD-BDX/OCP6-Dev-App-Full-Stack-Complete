package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.models.Topic;

/**
 * Mapper pour l'entité {@link Topic} et son DTO {@link TopicDto}.
 * Ce mapper est un composant de Spring, il utilise MapStruct pour mapper l'entité et le DTO.
 */
@Component
@Mapper(componentModel = "spring")
public interface TopicMapper extends EntityMapper<TopicDto, Topic> {
    // Les méthodes toDto et toEntity sont héritées de EntityMapper
}
