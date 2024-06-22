package com.openclassrooms.mddapi.payload.response;

/**
 * Classe représentant une réponse de message générique.
 * <p>
 * Cette classe est utilisée pour envoyer une réponse contenant un simple message à l'utilisateur.
 * Elle contient un seul champ, le message, qui peut être récupéré et modifié à l'aide des méthodes {@link #getMessage()} et {@link #setMessage(String)}.
 * 
 * @see #getMessage()
 * @see #setMessage(String)
 */
public class MessageResponse {
    private String message;

    /**
     * Constructeur pour {@link MessageResponse}.
     * <p>
     * Ce constructeur initialise un nouvel objet {@link MessageResponse} avec le message spécifié.
     * 
     * @param message Le message à inclure dans la réponse.
     * 
     * @see MessageResponse
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Récupère le message de cette réponse.
     * <p>
     * Cette méthode retourne le message actuellement défini pour cette réponse.
     * 
     * @return Le message de cette réponse.
     * 
     * @see #setMessage(String)
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message de cette réponse.
     * <p>
     * Cette méthode permet de modifier le message de cette réponse.
     * 
     * @param message Le nouveau message pour cette réponse.
     * 
     * @see #getMessage()
     */
    public void setMessage(String message) {
        this.message = message;
    }
}