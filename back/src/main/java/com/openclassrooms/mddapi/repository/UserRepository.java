package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.User;

/**
 * UserRepository est une interface qui étend JpaRepository pour fournir des méthodes CRUD pour l'entité User.
 * Elle définit également des méthodes personnalisées pour rechercher un utilisateur par email et vérifier l'existence d'un utilisateur par email ou nom d'utilisateur.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  /**
   * Recherche un utilisateur par son email.
   * 
   * @param email L'email de l'utilisateur à rechercher.
   * @return Un Optional contenant l'utilisateur si trouvé, sinon Optional vide.
   */
  Optional<User> findByEmail(String email);

  /**
   * Vérifie si un utilisateur existe avec l'email spécifié.
   * 
   * @param email L'email de l'utilisateur à vérifier.
   * @return true si un utilisateur avec l'email spécifié existe, sinon false.
   */
  Boolean existsByEmail(String email); 

  /**
   * Vérifie si un utilisateur existe avec l'email ou le nom d'utilisateur spécifié.
   * 
   * @param email L'email de l'utilisateur à vérifier.
   * @param username Le nom d'utilisateur à vérifier.
   * @return true si un utilisateur avec l'email ou le nom d'utilisateur spécifié existe, sinon false.
   */
  Boolean existsByEmailOrUsername(String email, String username);

  /**
   * Recherche un utilisateur par son email ou son nom d'utilisateur.
   * 
   * @param email L'email de l'utilisateur à rechercher.
   * @param username Le nom d'utilisateur à rechercher.
   * @return Un Optional contenant l'utilisateur si trouvé, sinon Optional vide.
   */
  Optional<User> findByEmailOrUsername(String email, String username);
}