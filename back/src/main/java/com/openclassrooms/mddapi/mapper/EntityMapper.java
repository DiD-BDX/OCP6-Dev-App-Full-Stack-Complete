package com.openclassrooms.mddapi.mapper;

import java.util.List;

/**
 * Interface générique pour mapper entre les objets DTO (Data Transfer Object) et les entités.
 *
 * @param <D> Le type de l'objet DTO.
 * @param <E> Le type de l'entité.
 */
public interface EntityMapper<D, E> {

    /**
     * Convertit un DTO en entité.
     *
     * @param dto L'objet DTO à convertir.
     * @return L'entité convertie.
     */
    E toEntity(D dto);

    /**
     * Convertit une entité en DTO.
     *
     * @param entity L'entité à convertir.
     * @return Le DTO converti.
     */
    D toDto(E entity);

    /**
     * Convertit une liste de DTOs en une liste d'entités.
     *
     * @param dtoList La liste de DTOs à convertir.
     * @return La liste d'entités converties.
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Convertit une liste d'entités en une liste de DTOs.
     *
     * @param entityList La liste d'entités à convertir.
     * @return La liste de DTOs convertis.
     */
    List<D> toDto(List<E> entityList);
}