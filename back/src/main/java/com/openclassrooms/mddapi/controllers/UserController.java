package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.AuthenticatedUserService;
import com.openclassrooms.mddapi.services.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur pour gérer les requêtes liées aux utilisateurs.
 * <p>
 * Cette classe est annotée avec {@link RestController} pour indiquer à Spring que c'est un contrôleur REST.
 * Elle est également annotée avec {@link RequestMapping} pour spécifier le chemin de base pour les routes dans ce contrôleur.
 * Enfin, elle est annotée avec {@link CrossOrigin} pour permettre les requêtes CORS de n'importe quelle origine.
 * @see RestController
 * @see RequestMapping
 * @see CrossOrigin
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticatedUserService authenticatedUserService;

    /**
     * Trouve un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à trouver.
     * @return Une {@link ResponseEntity} contenant le DTO de l'utilisateur si trouvé, sinon une réponse avec un statut 404.
     * @see UserService#findById(Long)
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
     * Met à jour l'utilisateur authentifié.
     *
     * @param user L'utilisateur avec les nouvelles informations.
     * @return Une {@link ResponseEntity} contenant le DTO de l'utilisateur mis à jour si réussi, sinon une réponse avec un statut 400.
     * @see UserService#updateUser(String, String, String)
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateAuthenticatedUser(@RequestBody User user) {
        try {
            // Récupérer l'utilisateur authentifié
            String currentUsername = authenticatedUserService.getAuthenticatedUsername();

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