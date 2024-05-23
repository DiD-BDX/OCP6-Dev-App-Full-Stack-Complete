package com.openclassrooms.mddapi.payload.response;

/**
 * Classe représentant une réponse de message générique.
 * <p>
 * Cette classe est utilisée pour envoyer une réponse contenant un simple message à l'utilisateur.
 * Elle contient un seul champ, le message, qui peut être récupéré et modifié à l'aide des méthodes getter et setter.
 */
public class MessageResponse {
    private String message;

    /**
     * Constructeur pour {@link MessageResponse}.
     *
     * @param message Le message à inclure dans la réponse.
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Récupère le message de cette réponse.
     *
     * @return Le message de cette réponse.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message de cette réponse.
     *
     * @param message Le nouveau message pour cette réponse.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}