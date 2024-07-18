package com.openclassrooms.mddapi.exception;

/**
 * Exception levée lorsque l'email fourni est déjà utilisé par un autre utilisateur.
 * Cette exception est une sous-classe de RuntimeException.
 */
public class EmailAlreadyTakenException extends RuntimeException {

    /**
     * Constructeur de l'exception EmailAlreadyTakenException.
     *
     * @param message le message détaillant la cause de l'exception
     */
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}