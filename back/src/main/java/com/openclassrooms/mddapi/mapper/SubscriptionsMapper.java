package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.models.Subscriptions;

/**
 * Interface pour le mapper entre l'entité {@link com.openclassrooms.mddapi.models.Subscriptions} et son DTO {@link com.openclassrooms.mddapi.dto.SubscriptionsDto}.
 * Ce mapper est un composant de Spring et utilise MapStruct pour effectuer les conversions.
 * L'annotation {@link org.springframework.stereotype.Component} indique que cette interface est un composant Spring.
 * L'annotation {@link org.mapstruct.Mapper} indique que cette interface est un mapper MapStruct.
 */
@Component
@Mapper(componentModel = "spring")
public interface SubscriptionsMapper {

    /**
     * Convertit l'entité {@link com.openclassrooms.mddapi.models.Subscriptions} en son DTO {@link com.openclassrooms.mddapi.dto.SubscriptionsDto}.
     * Les annotations {@link org.mapstruct.Mapping} indiquent comment les champs de l'entité sont mappés sur les champs du DTO.
     * @param subscriptions l'entité à convertir
     * @return le DTO converti
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "topic.name", target = "topicName")
    @Mapping(source = "topic.description", target = "topicDescription")
    SubscriptionsDto toDto(Subscriptions subscriptions);

    /**
     * Convertit le {@link com.openclassrooms.mddapi.dto.SubscriptionsDto} en son entité {@link com.openclassrooms.mddapi.models.Subscriptions}.
     * Les annotations {@link org.mapstruct.Mapping} indiquent comment les champs du DTO sont mappés sur les champs de l'entité.
     * @param subscriptionsDto le DTO à convertir
     * @return l'entité convertie
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "topicId", target = "topic.id")
    Subscriptions toEntity(SubscriptionsDto subscriptionsDto);
}