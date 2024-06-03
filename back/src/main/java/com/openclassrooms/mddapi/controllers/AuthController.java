package com.openclassrooms.mddapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

/**
 * Contrôleur pour les opérations d'authentification.
 * <p>
 * Ce contrôleur gère les requêtes d'authentification et d'inscription.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * Constructeur pour AuthController.
     *
     * @param authenticationManager Le gestionnaire d'authentification.
     * @param passwordEncoder L'encodeur de mot de passe.
     * @param jwtUtils L'utilitaire JWT.
     * @param userRepository Le dépôt d'utilisateurs.
     */
    AuthController(AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Authentifie un utilisateur.
     *
     * @param loginRequest Les détails de la demande de connexion.
     * @return Une réponse contenant le JWT si l'authentification réussit, ou un message d'erreur sinon.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Tentez d'authentifier l'utilisateur avec l'email ou le nom d'utilisateur et le mot de passe fournis
    Authentication authentication;
    try {
        // Essayez d'authentifier avec l'email
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    } catch (BadCredentialsException e) {
        // Si l'authentification par email échoue, essayez avec le nom d'utilisateur
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Recherchez l'utilisateur par email ou nom d'utilisateur
        User user = this.userRepository.findByEmailOrUsername(userDetails.getEmail(), userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    /**
     * Inscrit un nouvel utilisateur.
     *
     * @param signUpRequest Les détails de la demande d'inscription.
     * @return Une réponse indiquant si l'inscription a réussi ou non.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        
        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}