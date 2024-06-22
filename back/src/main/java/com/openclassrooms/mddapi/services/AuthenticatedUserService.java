package com.openclassrooms.mddapi.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service pour gérer les utilisateurs authentifiés.
 * <p>
 * Cette classe est annotée avec {@link Service} pour indiquer à Spring que c'est un service.
 * Elle fournit une méthode pour obtenir le nom d'utilisateur de l'utilisateur authentifié.
 * @see Service
 * @see SecurityContextHolder
 * @see Authentication
 */
@Service
public class AuthenticatedUserService {

    /**
     * Récupère le nom d'utilisateur de l'utilisateur authentifié.
     *
     * @return Le nom d'utilisateur de l'utilisateur authentifié.
     * @see SecurityContextHolder#getContext()
     * @see Authentication#getName()
     */
    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}