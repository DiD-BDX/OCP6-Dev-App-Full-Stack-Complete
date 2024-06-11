package com.openclassrooms.mddapi.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.SubscriptionsDto;
import com.openclassrooms.mddapi.payload.request.SubscribeRequest;
import com.openclassrooms.mddapi.services.SubscriptionService;

@RestController
@RequestMapping("/api/topics")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    /**
     * Abonne un utilisateur à un topic.
     *
     * @param id L'ID du topic.
     * @param userId L'ID de l'utilisateur.
     * @return Le topic auquel l'utilisateur a été abonné.
     */
    @PostMapping("/{id}/subscribe")
    public SubscriptionsDto subscribeUserToTopic(@PathVariable Long id, @RequestBody SubscribeRequest request) {
        return subscriptionService.subscribeUserToTopic(request.getUserId(), id);
    }

    @DeleteMapping("/{id}/unsubscribe")
    public void unsubscribeUserFromTopic(@PathVariable Long id, @RequestParam Long userId) {
        subscriptionService.unsubscribeUserFromTopic(userId, id);
    }

    /* @GetMapping("/{userId}/subscriptions")
    public List<SubscriptionsDto> getSubscriptionsByUserId(@PathVariable Long userId) {
        List<SubscriptionsDto> subscriptions = subscriptionService.getSubscriptionsByUserId(userId).stream()
            .map(subscription -> subscriptionService.toDto(subscription))
            .collect(Collectors.toList());
        System.out.println(subscriptions);
        return subscriptions;
    } */
    @GetMapping("/{userId}/subscriptions")
    public List<SubscriptionsDto> getSubscriptionsByUserId(@PathVariable Long userId) {
        return subscriptionService.getSubscriptionsByUserId(userId);
    }
}
