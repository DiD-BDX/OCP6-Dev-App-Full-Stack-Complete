package com.openclassrooms.mddapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;

/**
 * Service pour gérer l'authentification liée aux utilisateurs.
 */
@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authentifie un utilisateur en utilisant son email ou nom d'utilisateur et son mot de passe.
     *
     * @param emailOrUsername l'email ou le nom d'utilisateur de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return l'objet Authentication après authentification réussie
     */
    public Authentication authenticate(String emailOrUsername, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailOrUsername, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    /**
     * Trouve un utilisateur par son email.
     *
     * @param email l'email de l'utilisateur
     * @return l'utilisateur trouvé
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec cet email
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
    
    /**
     * Trouve un utilisateur par son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur de l'utilisateur
     * @return l'utilisateur trouvé
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec ce nom d'utilisateur
     */
    public User findUserByUsername(String username) {
        System.out.println("findUserByUsername" + username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    /**
     * Trouve un utilisateur par son email ou son nom d'utilisateur.
     *
     * @param email l'email de l'utilisateur
     * @param username le nom d'utilisateur de l'utilisateur
     * @return l'utilisateur trouvé
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec cet email ou nom d'utilisateur
     */
    public User findUserByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Génère un token JWT pour un utilisateur authentifié.
     *
     * @param authentication l'objet Authentication de l'utilisateur
     * @return le token JWT généré
     */
    public String generateJwtToken(Authentication authentication) {
        return jwtUtils.generateJwtToken(authentication);
    }

    /**
     * Vérifie si un utilisateur existe par son email.
     *
     * @param email l'email de l'utilisateur
     * @return true si l'utilisateur existe, false sinon
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Encode un mot de passe en utilisant un encodeur de mot de passe.
     *
     * @param password le mot de passe à encoder
     * @return le mot de passe encodé
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    /**
     * Sauvegarde un utilisateur dans le dépôt.
     *
     * @param user l'utilisateur à sauvegarder
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }
}