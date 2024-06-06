package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.models.Subscriptions;

/**
 * Interface pour le mapper entre l'entité {@link Subscriptions} et son DTO {@link SubscriptionsDto}.
 * Ce mapper est un composant de Spring et utilise MapStruct pour effectuer les conversions.
 */
@Component
@Mapper(componentModel = "spring")
public interface SubscriptionsMapper {

    /**
     * Convertit l'entité {@link Subscriptions} en son DTO {@link SubscriptionsDto}.
     * @param subscriptions l'entité à convertir
     * @return le DTO converti
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "topic.id", target = "topicId")
    SubscriptionsDto toDto(Subscriptions subscriptions);

    /**
     * Convertit le {@link SubscriptionsDto} en son entité {@link Subscriptions}.
     * @param subscriptionsDto le DTO à convertir
     * @return l'entité convertie
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "topicId", target = "topic.id")
    Subscriptions toEntity(SubscriptionsDto subscriptionsDto);
}