package com.openclassrooms.mddapi.mapper;

import java.util.List;

/**
 * Cette interface générique est utilisée pour mapper entre les objets DTO (Data Transfer Object) et les entités.
 * Elle définit des méthodes pour convertir un objet DTO en entité, une entité en DTO, une liste de DTOs en une liste d'entités, et une liste d'entités en une liste de DTOs.
 *
 * @param <D> Le type de l'objet DTO.
 * @param <E> Le type de l'entité.
 * @see com.openclassrooms.mddapi.dto
 * @see com.openclassrooms.mddapi.models
 */
public interface EntityMapper<D, E> {

    /**
     * Cette méthode convertit un objet DTO en entité.
     * L'implémentation spécifique de cette méthode dépendra du type de DTO et de l'entité.
     *
     * @param dto L'objet DTO à convertir.
     * @return L'entité convertie.
     * @see #toDto(Object)
     */
    E toEntity(D dto);

    /**
     * Cette méthode convertit une entité en DTO.
     * L'implémentation spécifique de cette méthode dépendra du type de DTO et de l'entité.
     *
     * @param entity L'entité à convertir.
     * @return Le DTO converti.
     * @see #toEntity(Object)
     */
    D toDto(E entity);

    /**
     * Cette méthode convertit une liste de DTOs en une liste d'entités.
     * Elle appelle la méthode {@link #toEntity(Object)} pour chaque DTO dans la liste.
     *
     * @param dtoList La liste de DTOs à convertir.
     * @return La liste d'entités converties.
     * @see #toDto(List)
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Cette méthode convertit une liste d'entités en une liste de DTOs.
     * Elle appelle la méthode {@link #toDto(Object)} pour chaque entité dans la liste.
     *
     * @param entityList La liste d'entités à convertir.
     * @return La liste de DTOs convertis.
     * @see #toEntity(List)
     */
    List<D> toDto(List<E> entityList);
}