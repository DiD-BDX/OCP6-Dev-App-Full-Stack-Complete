package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.exception.EmailAlreadyTakenException;
import com.openclassrooms.mddapi.exception.InvalidCredentialsException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.services.AuthService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequest the login request containing the user's credentials
     * @return a ResponseEntity containing a JwtResponse with the JWT token and user details
     * @throws InvalidCredentialsException if the email or username is invalid
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = getUserFromRequest(loginRequest);
        Authentication authentication = authService.authenticate(user.getEmail(), loginRequest.getPassword());
        String jwt = authService.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    /**
     * Registers a new user based on the provided signup request.
     *
     * @param signUpRequest the signup request containing the user's details
     * @return a ResponseEntity containing a MessageResponse indicating successful registration
     * @throws EmailAlreadyTakenException if the email is already taken
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (authService.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyTakenException("Error: Email is already taken!");
        }

        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                authService.encodePassword(signUpRequest.getPassword()));

        authService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Retrieves a user based on the provided login request.
     *
     * @param loginRequest the login request containing the user's email or username
     * @return the User object corresponding to the provided email or username
     * @throws InvalidCredentialsException if the email or username is invalid
     */
    private User getUserFromRequest(LoginRequest loginRequest) {
        User user = null;
        if (loginRequest.getEmailOrUsername() != null && !loginRequest.getEmailOrUsername().isEmpty()) {
            user = authService.findUserByEmailOrUsername(loginRequest.getEmailOrUsername(), loginRequest.getEmailOrUsername());
        }
        if (user == null) {
            throw new InvalidCredentialsException("Invalid email or username");
        }
        return user;
    }
}