package com.openclassrooms.mddapi.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Service pour gérer les utilisateurs.
 * <p>
 * Cette classe est annotée avec {@link Service @Service} pour indiquer à Spring que c'est un bean et
 * peut être injectée où nécessaire. Elle utilise {@link UserRepository UserRepository} pour effectuer des opérations
 * sur les utilisateurs dans la base de données.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructeur pour {@link UserService UserService}.
     *
     * @param userRepository Le repository à utiliser pour les opérations sur les utilisateurs.
     *                       Voir {@link UserRepository UserRepository}.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Trouve un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à trouver.
     * @return {@link User User} L'utilisateur si trouvé, sinon null.
     *         Voir {@link User User}.
     */
    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    /**
     * Met à jour un utilisateur avec un nouveau nom d'utilisateur et un nouvel email.
     *
     * @param currentUsername Le nom d'utilisateur actuel de l'utilisateur.
     * @param newUsername Le nouveau nom d'utilisateur.
     * @param newEmail Le nouvel email.
     * @return {@link User User} L'utilisateur mis à jour si trouvé et mis à jour avec succès, sinon null.
     *         Voir {@link User User}.
     */
    public User updateUser(String currentUsername, String newUsername, String newEmail) {
        Optional<User> optionalUser = this.userRepository.findByEmailOrUsername(currentUsername, currentUsername);
    
        User user = optionalUser.orElseThrow(() -> new NoSuchElementException("User not found with username: " + currentUsername));
        user.setUsername(newUsername);
        user.setEmail(newEmail);
        this.userRepository.save(user);
        return user;
    }

    /**
     * Trouve le nom d'utilisateur d'un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur.
     * @return Le nom d'utilisateur si l'utilisateur est trouvé, sinon null.
     */
    public String findUsernameById(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        return user != null ? user.getUsername() : null;
    }
}