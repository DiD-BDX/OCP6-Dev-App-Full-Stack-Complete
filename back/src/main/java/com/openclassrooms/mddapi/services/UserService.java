package com.openclassrooms.mddapi.services;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Service pour gérer les utilisateurs.
 * <p>
 * Cette classe est annotée avec {@link Service} pour indiquer à Spring que c'est un bean et
 * peut être injectée où nécessaire. Elle utilise {@link UserRepository} pour effectuer des opérations
 * sur les utilisateurs dans la base de données.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructeur pour {@link UserService}.
     *
     * @param userRepository Le repository à utiliser pour les opérations sur les utilisateurs.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Trouve un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à trouver.
     * @return L'utilisateur si trouvé, sinon null.
     */
    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }
}