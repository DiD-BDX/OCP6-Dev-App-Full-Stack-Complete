package com.openclassrooms.mddapi.security.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Implémentation de {@link UserDetails} pour la sécurité de l'application.
 * <p>
 * Cette classe est utilisée par Spring Security pour représenter les détails d'un utilisateur authentifié.
 * Elle implémente l'interface {@link UserDetails} qui est utilisée par Spring Security pour gérer les détails de l'utilisateur.
 * Elle contient des informations sur l'utilisateur comme son ID, son username, son email et son mot de passe.
 */
@Builder
@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    /**
     * Renvoie les autorités accordées à l'utilisateur.
     * <p>
     * Dans cette implémentation, une nouvelle collection vide est renvoyée à chaque appel.
     *
     * @return une collection d'objets {@link GrantedAuthority} représentant les autorités de l'utilisateur.
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {        
        return new HashSet<GrantedAuthority>();
    }


    // Les méthodes suivantes sont des implémentations des méthodes de l'interface UserDetails.
    // Dans cette implémentation, elles renvoient toutes true, indiquant que le compte est toujours actif, non verrouillé, et que les informations d'identification ne sont jamais expirées.

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Compare cet objet UserDetailsImpl à un autre objet.
     * <p>
     * La comparaison est basée sur l'égalité des ID des utilisateurs.
     *
     * @param o l'objet à comparer à cet objet UserDetailsImpl.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    } 
}