package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.User;

/**
 * Le {@link Repository} pour les {@link User}.
 * <p>
 * Cette interface étend {@link JpaRepository} pour fournir des méthodes de gestion de base de données pour les {@link User}.
 * Elle définit également des méthodes personnalisées pour rechercher un utilisateur par email et vérifier l'existence d'un utilisateur par email ou nom d'utilisateur.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  /**
   * Recherche un {@link User} par son email.
   * 
   * @param email L'email du {@link User} à rechercher.
   * @return Un {@link Optional} contenant le {@link User} si trouvé, sinon {@link Optional} vide.
   */
  Optional<User> findByEmail(String email);

  /**
   * Vérifie si un {@link User} existe avec l'email spécifié.
   * 
   * @param email L'email du {@link User} à vérifier.
   * @return true si un {@link User} avec l'email spécifié existe, sinon false.
   */
  Boolean existsByEmail(String email); 

  /**
   * Vérifie si un {@link User} existe avec l'email ou le nom d'utilisateur spécifié.
   * 
   * @param email L'email du {@link User} à vérifier.
   * @param username Le nom d'utilisateur à vérifier.
   * @return true si un {@link User} avec l'email ou le nom d'utilisateur spécifié existe, sinon false.
   */
  Boolean existsByEmailOrUsername(String email, String username);

  /**
   * Recherche un {@link User} par son email ou son nom d'utilisateur.
   * 
   * @param email L'email du {@link User} à rechercher.
   * @param username Le nom d'utilisateur à rechercher.
   * @return Un {@link Optional} contenant le {@link User} si trouvé, sinon {@link Optional} vide.
   */
  Optional<User> findByEmailOrUsername(String email, String username);
}