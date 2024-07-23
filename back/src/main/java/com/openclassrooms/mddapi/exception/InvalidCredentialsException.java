package com.openclassrooms.mddapi.exception;

/**
 * Exception levée lorsque les informations d'identification fournies sont invalides.
 * Cette exception est une sous-classe de RuntimeException.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructeur de l'exception InvalidCredentialsException.
     *
     * @param message le message détaillant la cause de l'exception
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}