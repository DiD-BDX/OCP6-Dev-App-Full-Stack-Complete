package com.openclassrooms.mddapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.mddapi.payload.response.MessageResponse;

/**
 * Gestionnaire global des exceptions pour l'application.
 * Ce gestionnaire intercepte les exceptions spécifiques et renvoie des réponses HTTP appropriées.
 */
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions de type BadCredentialsException.
     *
     * @param ex l'exception BadCredentialsException interceptée
     * @return une réponse HTTP avec le statut UNAUTHORIZED et un message d'erreur
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Error: Invalid credentials!"));
    }

    /**
     * Gère les exceptions de type UsernameNotFoundException.
     *
     * @param ex l'exception UsernameNotFoundException interceptée
     * @return une réponse HTTP avec le statut NOT_FOUND et un message d'erreur
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<MessageResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Error: User not found!"));
    }

    /**
     * Gère les exceptions de type EmailAlreadyTakenException.
     *
     * @param ex l'exception EmailAlreadyTakenException interceptée
     * @return une réponse HTTP avec le statut BAD_REQUEST et un message d'erreur
     */
    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<MessageResponse> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse(ex.getMessage()));
    }

    /**
     * Gère les exceptions de type InvalidCredentialsException.
     *
     * @param ex l'exception InvalidCredentialsException interceptée
     * @return une réponse HTTP avec le statut UNAUTHORIZED et un message d'erreur
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<MessageResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse(ex.getMessage()));
    }
}