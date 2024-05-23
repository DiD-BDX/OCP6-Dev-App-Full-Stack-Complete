package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.User;

/**
 * Interface de repository pour {@link User}.
 * <p>
 * Cette interface étend {@link JpaRepository}, qui fournit des méthodes pour les opérations CRUD
 * sur l'entité {@link User}. Elle est annotée avec {@link Repository} pour indiquer à Spring que c'est un bean
 * et peut être injectée où nécessaire.
 * <p>
 * Elle définit également deux méthodes supplémentaires pour trouver un utilisateur par email et pour vérifier
 * si un utilisateur existe par email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  /**
   * Trouve un utilisateur par email.
   *
   * @param email L'email de l'utilisateur à trouver.
   * @return Un {@link Optional} qui contient l'utilisateur s'il est trouvé, sinon vide.
   */
  Optional<User> findByEmail(String email);

  /**
   * Vérifie si un utilisateur existe par email.
   *
   * @param email L'email de l'utilisateur à vérifier.
   * @return Un booléen indiquant si un utilisateur avec cet email existe.
   */
  Boolean existsByEmail(String email); 
}