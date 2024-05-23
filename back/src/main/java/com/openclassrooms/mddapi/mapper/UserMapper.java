package com.openclassrooms.mddapi.mapper;

import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;

/**
 * Mapper pour convertir entre {@link UserDto} et {@link User}.
 * <p>
 * Cette interface étend {@link EntityMapper}, qui définit des méthodes pour mapper entre
 * un type de DTO et un type d'entité. Les types spécifiques pour cette interface sont {@link UserDto} pour le DTO
 * et {@link User} pour l'entité.
 * <p>
 * Cette interface est annotée avec {@link Component} pour indiquer à Spring que c'est un bean et
 * peut être injectée où nécessaire. Elle est également annotée avec {@link Mapper} pour indiquer à MapStruct
 * qu'il s'agit d'un mapper et que MapStruct doit générer une implémentation à la compilation.
 */
@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
}