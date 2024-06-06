package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur pour les opérations liées aux topics.
 * Toutes les méthodes de ce contrôleur sont mappées sur l'URL "/api/topics".
 */
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    /**
     * Constructeur pour TopicController.
     *
     * @param topicService Le service à utiliser pour les opérations liées aux topics.
     */
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Récupère la liste de tous les topics.
     *
     * @return Une liste de tous les topics.
     */
    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }
}