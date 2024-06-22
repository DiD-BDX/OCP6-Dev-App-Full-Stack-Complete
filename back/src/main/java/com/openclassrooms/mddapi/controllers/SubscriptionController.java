package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.payload.request.SubscribeRequest;
import com.openclassrooms.mddapi.services.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour les opérations sur les abonnements.
 * <p>
 * Ce contrôleur gère les requêtes pour s'abonner, se désabonner et obtenir les abonnements d'un utilisateur.
 * @see com.openclassrooms.mddapi.controllers
 */
@RestController
@RequestMapping("/api/topics")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * Constructeur pour SubscriptionController.
     *
     * @param subscriptionService Le service d'abonnement.
     * @see SubscriptionService
     */
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Abonne un utilisateur à un sujet.
     *
     * @param id L'ID du sujet.
     * @param request Les détails de l'abonnement.
     * @return Les détails de l'abonnement.
     * @see SubscribeRequest
     * @see SubscriptionsDto
     */
    @PostMapping("/{id}/subscribe")
    public SubscriptionsDto subscribeUserToTopic(@PathVariable Long id, @RequestBody SubscribeRequest request) {
        return subscriptionService.subscribeUserToTopic(request.getUserId(), id);
    }

    /**
     * Désabonne un utilisateur d'un sujet.
     *
     * @param id L'ID du sujet.
     * @param userId L'ID de l'utilisateur.
     */
    @DeleteMapping("/{id}/unsubscribe")
    public void unsubscribeUserFromTopic(@PathVariable Long id, @RequestParam Long userId) {
        subscriptionService.unsubscribeUserFromTopic(userId, id);
    }

    /**
     * Obtient les abonnements d'un utilisateur.
     *
     * @param userId L'ID de l'utilisateur.
     * @return Une liste d'abonnements.
     * @see SubscriptionsDto
     */
    @GetMapping("/{userId}/subscriptions")
    public List<SubscriptionsDto> getSubscriptionsByUserId(@PathVariable Long userId) {
        return subscriptionService.getSubscriptionsByUserId(userId);
    }
}