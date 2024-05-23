package com.openclassrooms.mddapi.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Implémentation de {@link UserDetailsService} pour la sécurité de l'application.
 * <p>
 * Cette classe est utilisée par Spring Security pour charger les détails d'un utilisateur par son email.
 * Elle implémente l'interface {@link UserDetailsService} qui est utilisée par Spring Security pour gérer les détails de l'utilisateur.
 * Elle contient une référence à un {@link UserRepository} pour interagir avec la base de données.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    /**
     * Constructeur pour {@link UserDetailsServiceImpl}.
     *
     * @param userRepository Le repository à utiliser pour les opérations liées aux utilisateurs.
     */
    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge un utilisateur par son email.
     *
     * @param email L'email de l'utilisateur à charger.
     * @return Un objet {@link UserDetails} représentant l'utilisateur chargé.
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec l'email fourni.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl
                .builder()
                .id(user.getId())
                .username(user.getUserName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}