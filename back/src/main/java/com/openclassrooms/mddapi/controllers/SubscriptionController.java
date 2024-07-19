package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.mapper.SubscriptionsMapper;
import com.openclassrooms.mddapi.models.Subscriptions;
import com.openclassrooms.mddapi.payload.request.SubscribeRequest;
import com.openclassrooms.mddapi.services.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur pour les opérations sur les abonnements.
 * <p>
 * Ce contrôleur gère les requêtes pour s'abonner, se désabonner et obtenir les abonnements d'un utilisateur.
 * </p>
 */
@RestController
@RequestMapping("/api/topics")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final SubscriptionsMapper subscriptionsMapper;

    /**
     * Constructeur pour SubscriptionController.
     *
     * @param subscriptionService Le service d'abonnement.
     * @param subscriptionsMapper Le mapper pour les abonnements.
     */
    public SubscriptionController(SubscriptionService subscriptionService, SubscriptionsMapper subscriptionsMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionsMapper = subscriptionsMapper;
    }

    /**
     * Abonne un utilisateur à un sujet.
     *
     * @param id L'ID du sujet.
     * @param request Les détails de l'abonnement.
     * @return Les détails de l'abonnement sous forme de DTO.
     */
    @PostMapping("/{id}/subscribe")
    public SubscriptionsDto subscribeUserToTopic(@PathVariable Long id, @RequestBody SubscribeRequest request) {
        Subscriptions subscription = subscriptionService.subscribeUserToTopic(request.getUserId(), id);
        return subscriptionsMapper.toDto(subscription);
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
     * @return Une liste d'abonnements sous forme de DTO.
     */
    @GetMapping("/{userId}/subscriptions")
    public List<SubscriptionsDto> getSubscriptionsByUserId(@PathVariable Long userId) {
        List<Subscriptions> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return subscriptions.stream()
                .map(subscriptionsMapper::toDto)
                .collect(Collectors.toList());
    }
}