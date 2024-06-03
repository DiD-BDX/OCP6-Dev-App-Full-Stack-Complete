package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;

/**
 * Contrôleur pour gérer les requêtes liées aux utilisateurs.
 * <p>
 * Cette classe est annotée avec {@link RestController} pour indiquer à Spring que c'est un contrôleur REST.
 * Elle est également annotée avec {@link RequestMapping} pour spécifier le chemin de base pour les routes dans ce contrôleur.
 * Enfin, elle est annotée avec {@link CrossOrigin} pour permettre les requêtes CORS de n'importe quelle origine.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * Constructeur pour {@link UserController}.
     *
     * @param userService Le service à utiliser pour les opérations liées aux utilisateurs.
     * @param userMapper Le mapper à utiliser pour convertir les utilisateurs en DTOs.
     */
    public UserController(UserService userService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    /**
     * Trouve un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à trouver.
     * @return Une {@link ResponseEntity} contenant le DTO de l'utilisateur si trouvé, sinon une réponse avec un statut 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            User user = this.userService.findById(Long.valueOf(id));

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
 * Met à jour les informations d'un utilisateur authentifié.
 *
 * @param username Le nouveau nom d'utilisateur.
 * @param email Le nouvel email.
 * @return Une {@link ResponseEntity} contenant le DTO de l'utilisateur mis à jour si la mise à jour a réussi, sinon une réponse avec un statut 400.
 */
@PutMapping("/update")
public ResponseEntity<?> updateAuthenticatedUser(@RequestBody User user) {
    try {
        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Mettre à jour l'utilisateur
        User updatedUser = this.userService.updateUser(currentUsername, user.getUsername(), user.getEmail());

        if (updatedUser == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(this.userMapper.toDto(updatedUser));
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}
}